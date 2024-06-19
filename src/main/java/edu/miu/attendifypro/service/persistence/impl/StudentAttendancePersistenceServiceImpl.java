package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.CourseOffering;
import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import edu.miu.attendifypro.repository.StudentAttendanceRecordRepository;
import edu.miu.attendifypro.service.persistence.CourseOfferingPersistenceService;
import edu.miu.attendifypro.service.persistence.StudentAttendancePersistenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * @author kush
 */
@Service
@Transactional
public class StudentAttendancePersistenceServiceImpl implements StudentAttendancePersistenceService {

    final StudentAttendanceRecordRepository repository;
    final CourseOfferingPersistenceService courseOfferingPersistenceService;

    public StudentAttendancePersistenceServiceImpl(StudentAttendanceRecordRepository repository, CourseOfferingPersistenceService courseOfferingPersistenceService) {
        this.repository = repository;
        this.courseOfferingPersistenceService = courseOfferingPersistenceService;
    }

    @Override
    public List<StudentAttendanceRecord> getAttendanceRecords(Long offeringId,CourseOffering courseOffering) {
        return repository.findAllByLocationAndScanDateTimeBetween(courseOffering.getLocation(),
                courseOffering.getStartDate().atStartOfDay(), courseOffering.getEndDate().atStartOfDay());
    }
}
