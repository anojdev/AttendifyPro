package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentAttendanceRecordRepository extends JpaRepository<StudentAttendanceRecord, Long> {
//    List<StudentAttendanceRecord> findByStudentId(long student_id);
    @Query("select sa from  StudentAttendanceRecord sa inner join sa.student s where s.firstName = :name")
    List<StudentAttendanceRecord> findByStudentId(String name);

    //uncomment once security is working
//    @Query("select sa from  StudentAttendanceRecord sa inner join sa.student s where s.studentId = :studentId")
//    List<StudentAttendanceRecord> findByStudentId(String studentId);
}
