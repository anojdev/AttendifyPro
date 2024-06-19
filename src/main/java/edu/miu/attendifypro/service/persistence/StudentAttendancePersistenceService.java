package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.CourseOffering;
import edu.miu.attendifypro.domain.StudentAttendanceRecord;

import java.util.List;

public interface StudentAttendancePersistenceService {


    List<StudentAttendanceRecord> getAttendanceRecords(Long offeringId, CourseOffering courseOffering);
}
