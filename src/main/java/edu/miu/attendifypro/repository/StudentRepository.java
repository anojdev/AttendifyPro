package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByStudentId(String studentId);

    @Query("SELECT DISTINCT s FROM StudentCourseSelection s " +
            "JOIN FETCH s.student st " +
            "JOIN FETCH s.courseOffering co " +
            "WHERE st.studentId = :studentId")
    Optional<Student> findStudentCourses(String studentId);
}
