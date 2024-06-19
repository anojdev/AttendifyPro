package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Location;
import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kush
 */
public interface StudentAttendanceRecordRepository extends JpaRepository<StudentAttendanceRecord,Long> {


    List<StudentAttendanceRecord> findAllByLocationAndScanDateTimeBetween(Location location, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
