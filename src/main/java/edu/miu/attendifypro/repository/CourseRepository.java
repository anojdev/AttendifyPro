package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {

    Optional<Course> findByCourseCode(String courseCode);
}
