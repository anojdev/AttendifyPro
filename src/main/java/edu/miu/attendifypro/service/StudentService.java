package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.CourseDto;
import edu.miu.attendifypro.dto.StudentDto;
import edu.miu.attendifypro.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDto);
    public StudentDto getStudent(String studentId);
    public Collection<StudentDto> getAllStudents();
    public StudentDto updateStudent(StudentDto studentDto);
    public void deleteStudent(String studentId);

}
