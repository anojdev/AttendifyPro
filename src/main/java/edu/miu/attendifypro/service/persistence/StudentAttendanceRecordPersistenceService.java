package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.StudentAttendanceRecord;

import java.util.List;

public interface StudentAttendanceRecordPersistenceService {
     List<StudentAttendanceRecord> getAttendanceRecordsByStudentId(String studentId);
}
