package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.domain.CourseOffering;
import edu.miu.attendifypro.domain.Session;
import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import edu.miu.attendifypro.dto.response.AttendanceReportDto;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.persistence.CourseOfferingPersistenceService;
import edu.miu.attendifypro.service.persistence.StudentAttendancePersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public ServiceResponse<List<AttendanceReportDto>> getAttendanceRecords(Long offeringId) {

        Optional<CourseOffering> courseOfferingOpt=courseOfferingPersistenceService.findById(offeringId);
        if(courseOfferingOpt.isEmpty()){
            return ServiceResponse.of(AppStatusCode.E40004);
        }
        CourseOffering courseOffering=courseOfferingOpt.get();
        List<StudentAttendanceRecord> records=service.getAttendanceRecords(offeringId,courseOffering);
        List<Session> sessions=courseOffering.getSessions();
        List<AttendanceReportDto> attendanceReport=generateAttendanceReport(records,sessions);
        return ServiceResponse.of(attendanceReport, AppStatusCode.S20000);
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
}
