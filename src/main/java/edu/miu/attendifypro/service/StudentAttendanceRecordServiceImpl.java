package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.Student;
import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import edu.miu.attendifypro.dto.StudentAttendanceRecordDto;
import edu.miu.attendifypro.mapper.StudentAttendanceRecordMapper;
import edu.miu.attendifypro.repository.StudentAttendanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentAttendanceRecordServiceImpl implements StudentAttendanceRecordService {
    @Autowired
    private StudentAttendanceRecordRepository studentAttendanceRecordRepository;

    @Autowired
    private StudentAttendanceRecordMapper studentAttendanceRecordMapper;

    @Override
    public StudentAttendanceRecordDto createStudentAttendanceRecord(StudentAttendanceRecordDto studentAttendanceRecordDto) {
        StudentAttendanceRecord studentAttendanceRecord = studentAttendanceRecordMapper.toEntity(studentAttendanceRecordDto);
        StudentAttendanceRecord savedStudentAttendanceRecord = studentAttendanceRecordRepository.save(studentAttendanceRecord);
        return studentAttendanceRecordMapper.toDTO(savedStudentAttendanceRecord);
    }
}
