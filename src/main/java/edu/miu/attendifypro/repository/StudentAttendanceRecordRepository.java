package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Location;
import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kush
 */
public interface StudentAttendanceRecordRepository extends JpaRepository<StudentAttendanceRecord,Long> {


    List<StudentAttendanceRecord> findAllByLocationAndScanDateTimeBetween(Location location,
                                                                          LocalDateTime startDateTime,
                                                                          LocalDateTime endDateTime);

    @Query("SELECT sar FROM StudentAttendanceRecord sar " +
            "WHERE sar.location = :location " +
            "AND sar.scanDateTime BETWEEN :startDateTime AND :endDateTime " +
            "AND sar.student.id = :studentId")
    List<StudentAttendanceRecord> findAttendanceForStudent(@Param("location") Location location,
                                                           @Param("startDateTime") LocalDateTime startDateTime,
                                                           @Param("endDateTime") LocalDateTime endDateTime,
                                                           @Param("studentId") Long studentId);

    @Query("select sa from  StudentAttendanceRecord sa inner join sa.student s where s.id = :studentId")
    List<StudentAttendanceRecord> findByStudentId(Long studentId);


}
