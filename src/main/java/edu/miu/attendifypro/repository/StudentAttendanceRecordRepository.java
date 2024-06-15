package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAttendanceRecordRepository extends JpaRepository<StudentAttendanceRecord, Long> {
}
