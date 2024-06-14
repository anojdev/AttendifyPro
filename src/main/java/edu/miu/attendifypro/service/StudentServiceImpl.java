package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.Student;
import edu.miu.attendifypro.dto.StudentDto;
import edu.miu.attendifypro.mapper.StudentMapper;
import edu.miu.attendifypro.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDTO(savedStudent);
    }

    @Override
    public Collection<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public StudentDto getStudent(String studentId) {
        return studentRepository.findById(Long.parseLong(studentId))
                .map(studentMapper::toDTO)
                .orElse(null);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        Student updatedStudent = studentRepository.save(student);
        return studentMapper.toDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(String studentId) {
        studentRepository.deleteById(Long.parseLong(studentId));
    }
}
