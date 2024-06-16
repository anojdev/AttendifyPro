package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.repository.CourseRepository;
import edu.miu.attendifypro.service.persistence.CoursePersistenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class LocationPersistenceService implements LocationPersistenceService {

    private final CourseRepository courseRepository;


    public CoursePersistenceServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Optional<Course> findByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }

    @Override
    public List<Course> findAllById(HashSet<Long> uniqueRequisiteIds) {
        return courseRepository.findAllById(uniqueRequisiteIds);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void delete(Course course) {
        courseRepository.delete(course);
    }
}
