package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentAttendanceRecordRepository extends JpaRepository<StudentAttendanceRecord, Long> {
//    List<StudentAttendanceRecord> findByStudentId(long student_id);
    @Query("select e from  StudentAttendanceRecord e inner join e.student s where s.firstName = :name")
    List<StudentAttendanceRecord> findByStudentId(String name);
}
