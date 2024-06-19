package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentPersistenceService {
    List<Student> findAll();

    Page<Student> findAll(Pageable pageable);

    Optional<Student> findByStudentId(String studentId);

    Student save(Student student);

    void delete(Student student);

    Optional<Student> findById(Long id);

    Optional<Student> findByAccountId(Long id);
}
