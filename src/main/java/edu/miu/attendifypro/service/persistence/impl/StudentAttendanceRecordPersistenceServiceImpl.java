package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import edu.miu.attendifypro.repository.StudentAttendanceRecordRepository;
import edu.miu.attendifypro.service.persistence.StudentAttendanceRecordPersistenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentAttendanceRecordPersistenceServiceImpl implements StudentAttendanceRecordPersistenceService {

    private final StudentAttendanceRecordRepository studentAttendanceRecordRepository;


    public StudentAttendanceRecordPersistenceServiceImpl(StudentAttendanceRecordRepository studentAttendanceRecordRepository) {
        this.studentAttendanceRecordRepository = studentAttendanceRecordRepository;
    }
    @Override
    public List<StudentAttendanceRecord> getAttendanceRecordsByStudentId(String studentId) {
        return studentAttendanceRecordRepository.findByStudentId(studentId);
    }

}
