package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.domain.CourseOffering;
import edu.miu.attendifypro.domain.Session;
import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import edu.miu.attendifypro.dto.response.AttendanceReportDto;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.persistence.CourseOfferingPersistenceService;
import edu.miu.attendifypro.service.persistence.StudentAttendancePersistenceService;
import org.antlr.v4.runtime.misc.Triple;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public AttendanceServiceImpl(StudentAttendancePersistenceService service, CourseOfferingPersistenceService courseOfferingPersistenceService) {
        this.service = service;
        this.courseOfferingPersistenceService = courseOfferingPersistenceService;
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

    public List<AttendanceReportDto> generateAttendanceReport(List<StudentAttendanceRecord> attendanceRecords, List<Session> sessions) {
        List<AttendanceReportDto> reportList = new ArrayList<>();

        for (StudentAttendanceRecord record : attendanceRecords) {
            LocalDateTime scanDateTime = record.getScanDateTime();
            LocalDate scanDate = scanDateTime.toLocalDate();
            Long studentId = record.getStudent().getId();
            String studentName = record.getStudent().getFirstName()+" "+record.getStudent().getLastName();

            boolean isPresent = false;
            for (Session session : sessions) {
                LocalDateTime sessionStart = session.getStartDateTime();
                LocalDateTime sessionEnd = session.getEndDateTime();
                LocalDate sessionDate = sessionStart.toLocalDate();

                if (scanDate.equals(sessionDate) && !scanDateTime.isBefore(sessionStart) && !scanDateTime.isAfter(sessionEnd)) {
                    isPresent = true;
                    break;
                }
            }

            AttendanceReportDto reportDto = new AttendanceReportDto(studentId,studentName, scanDateTime, scanDate, isPresent);
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
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
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
        headerCell.setCellValue("Scan Date Time");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Day");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Present");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        int rowIndex = 1;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (AttendanceReportDto attendanceReportDto : attendanceReportDtos) {
            Row row = sheet.createRow(rowIndex++);

            Cell cell = row.createCell(0);
            cell.setCellValue(attendanceReportDto.getStudentId());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(attendanceReportDto.getStudentName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(attendanceReportDto.getScanDateTime().format(dateTimeFormatter));
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(attendanceReportDto.getDay());
            cell.setCellStyle(style);

            cell = row.createCell(4);
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

    /*{


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + locationPath.getFileName().toString());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(locationPath.toFile().length()));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(locationPath.toFile().length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }*/
}
