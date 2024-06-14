package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
