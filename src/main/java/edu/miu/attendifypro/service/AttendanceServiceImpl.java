package edu.miu.attendifypro.service;

import edu.miu.attendifypro.config.ContextUser;
import edu.miu.attendifypro.domain.*;
import edu.miu.attendifypro.dto.response.AttendanceReportDto;
import edu.miu.attendifypro.dto.response.StudentAttendanceRecordResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.mapper.StudentAttendanceRecordDtoMapper;
import edu.miu.attendifypro.service.persistence.CourseOfferingPersistenceService;
import edu.miu.attendifypro.service.persistence.StudentAttendancePersistenceService;
import edu.miu.attendifypro.service.persistence.StudentPersistenceService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    final
    StudentAttendancePersistenceService service;
    final CourseOfferingPersistenceService courseOfferingPersistenceService;
    final StudentPersistenceService studentPersistenceService;

    final ContextUser user;

    public AttendanceServiceImpl(StudentAttendancePersistenceService service, CourseOfferingPersistenceService courseOfferingPersistenceService, StudentPersistenceService studentPersistenceService, ContextUser user) {
        this.service = service;
        this.courseOfferingPersistenceService = courseOfferingPersistenceService;
        this.studentPersistenceService = studentPersistenceService;
        this.user = user;
    }


    @Override
    public ServiceResponse<String> getAttendanceRecords(Long offeringId) {

        Optional<CourseOffering> courseOfferingOpt=courseOfferingPersistenceService.findById(offeringId);
        if(courseOfferingOpt.isEmpty()){
            return ServiceResponse.of(AppStatusCode.E40004);
        }
        CourseOffering courseOffering=courseOfferingOpt.get();
        List<StudentAttendanceRecord> records=service.getAttendanceRecords(offeringId,courseOffering);
        List<Session> sessions=courseOffering.getSessions();
        List<AttendanceReportDto> attendanceReport=generateAttendanceReport(records,sessions);
        String reportPath=createExcelReport(attendanceReport,courseOffering);
        return ServiceResponse.of(reportPath, AppStatusCode.S20000);
    }

    @Override
    public ServiceResponse<List<AttendanceReportDto>> getStudentAttendanceRecords(Long offeringId) {
        Optional<CourseOffering> courseOfferingOpt=courseOfferingPersistenceService.findById(offeringId);
        if(courseOfferingOpt.isEmpty()){
            return ServiceResponse.of(AppStatusCode.E40004);
        }
        List<AttendanceReportDto> attendanceReport=new ArrayList<>();
        if(courseOfferingOpt.get().getStartDate().isAfter(LocalDate.now())){
            return ServiceResponse.of(attendanceReport, AppStatusCode.S20000);
        }
        CourseOffering courseOffering=courseOfferingOpt.get();
        Optional<Student> studentOpt=studentPersistenceService.findByAccountId(user.getUser().getId());
        List<StudentAttendanceRecord> records=service.getStudentAttendanceRecords(offeringId,courseOffering,studentOpt.get().getId());
        List<Session> sessions=courseOffering.getSessions();
        attendanceReport=generateAttendanceReport(records,sessions);
        return ServiceResponse.of(attendanceReport, AppStatusCode.S20000);
    }

    @Override
    public ServiceResponse<List<StudentAttendanceRecordResponse>> getSingleStudentAttendanceRecord() {
        try {
            Optional<Student> studentOpt=studentPersistenceService.findByAccountId(user.getUser().getId());
            List<StudentAttendanceRecord> studentAttendanceList = service.getSingleStudentAttendanceRecords(studentOpt.get().getId());
            List<StudentAttendanceRecordResponse> studentAttendanceResponses = studentAttendanceList.stream().map(StudentAttendanceRecordDtoMapper.dtoMapper::studentAttendanceRecordResponse).toList();
            return ServiceResponse.of(studentAttendanceResponses, AppStatusCode.S20000);
        }catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50001);
        }
    }

    public List<AttendanceReportDto> generateAttendanceReport(List<StudentAttendanceRecord> attendanceRecords, List<Session> sessions) {
        List<AttendanceReportDto> reportList = new ArrayList<>();

        DateTimeFormatter sessionFormatter = DateTimeFormatter.ofPattern("HH:mm");
        for (StudentAttendanceRecord record : attendanceRecords) {
            LocalDateTime scanDateTime = record.getScanDateTime();
            LocalDate scanDate = scanDateTime.toLocalDate();
            String studentId = record.getStudent().getStudentId();
            String studentName = record.getStudent().getFirstName()+" "+record.getStudent().getLastName();

            boolean isPresent = false;
            LocalDateTime sessionStart = null;
            LocalDateTime sessionEnd = null;
            for (Session session : sessions) {
                sessionStart = session.getStartDateTime();
                sessionEnd = session.getEndDateTime();
                LocalDate sessionDate = sessionStart.toLocalDate();

                if (scanDate.equals(sessionDate) && !scanDateTime.isBefore(sessionStart.minusMinutes(15)) && !scanDateTime.isAfter(sessionEnd)) {
                    isPresent = true;
                    break;
                }
            }

            AttendanceReportDto reportDto = new AttendanceReportDto(studentId,studentName, scanDateTime, scanDate, isPresent,sessionStart.format(sessionFormatter)+"-"+sessionEnd.format(sessionFormatter));
            reportList.add(reportDto);
        }

        return reportList;
    }

    public String createExcelReport(List<AttendanceReportDto> attendanceReportDtos, CourseOffering courseOffering) {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Attendance Report for " + courseOffering.getStartDate() + "-" + courseOffering.getEndDate());
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Student Id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Student Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Session");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Scan Date Time");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Date");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Present");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        int rowIndex = 1;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (AttendanceReportDto attendanceReportDto : attendanceReportDtos) {
            Row row = sheet.createRow(rowIndex++);

            Cell cell = row.createCell(0);
            cell.setCellValue(attendanceReportDto.getStudentId());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(attendanceReportDto.getStudentName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(attendanceReportDto.getSessionTime());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(attendanceReportDto.getScanDateTime().format(dateTimeFormatter));
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(attendanceReportDto.getDay().format(dateFormatter));
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(attendanceReportDto.isPresent() ? "Y" : "N");
            cell.setCellStyle(style);
        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "attendanceRecords.xlsx";

        try (FileOutputStream outputStream = new FileOutputStream(fileLocation)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileLocation;
    }

}
