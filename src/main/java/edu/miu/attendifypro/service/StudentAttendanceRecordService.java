package edu.miu.attendifypro.service;
import edu.miu.attendifypro.dto.StudentAttendanceRecordDto;
import org.springframework.stereotype.Service;

public interface StudentAttendanceRecordService {
    StudentAttendanceRecordDto createStudentAttendanceRecord(StudentAttendanceRecordDto studentAttendanceRecordDto);
}
