package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * @author kush
 */
public interface CoursePersistenceService {
    List<Course> findAll();

    Page<Course> findAll(Pageable pageable);

    Optional<Course> findById(Long id);

    Optional<Course> findByCourseCode(String courseCode);

    List<Course> findAllById(HashSet<Long> uniqueRequisiteIds);

    Course save(Course course);

    void delete(Course course);
}
