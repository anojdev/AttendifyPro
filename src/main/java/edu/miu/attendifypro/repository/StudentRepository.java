package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(String studentId);
    Optional<Student> findByAccount_Id(Long accountId);
}
