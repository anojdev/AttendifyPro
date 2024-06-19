package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.response.AttendanceReportDto;
import edu.miu.attendifypro.dto.response.StudentAttendanceRecordResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;

import java.util.List;

public interface AttendanceService {

    ServiceResponse<String> getAttendanceRecords(Long offeringId);

    ServiceResponse<List<AttendanceReportDto>> getStudentAttendanceRecords(Long offeringId);

    ServiceResponse<List<StudentAttendanceRecordResponse>> getSingleStudentAttendanceRecord();
}
