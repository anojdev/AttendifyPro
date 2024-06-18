package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.response.StudentAttendanceRecordResponse;
import edu.miu.attendifypro.dto.response.StudentResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;

import java.util.List;

public interface StudentAttendanceRecordService {
    ServiceResponse<List<StudentAttendanceRecordResponse>> getStudentAttendanceRecord(String studentId);
}
