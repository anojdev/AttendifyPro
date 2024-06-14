package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
