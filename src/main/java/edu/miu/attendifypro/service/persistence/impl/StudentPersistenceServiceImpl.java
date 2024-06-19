package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.Student;
import edu.miu.attendifypro.repository.StudentRepository;
import edu.miu.attendifypro.service.persistence.StudentPersistenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentPersistenceServiceImpl implements StudentPersistenceService {
    private final StudentRepository studentRepository;


    public StudentPersistenceServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Optional<Student> findByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(Student student) {
        studentRepository.delete(student);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> findByAccountId(Long id) {
        return studentRepository.findByAccount_Id(id);
    }
}
