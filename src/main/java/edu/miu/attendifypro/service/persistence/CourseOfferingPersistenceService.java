package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.domain.CourseOffering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface CourseOfferingPersistenceService {
    List<CourseOffering> findAll();

    Page<CourseOffering> findAll(Pageable pageable);
    List<Course> getCoursesByDate(LocalDate date);
    Optional<CourseOffering> findById(Long id);
    CourseOffering save(CourseOffering courseOffering);

    void delete(CourseOffering courseOffering);
}
