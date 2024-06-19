package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.domain.CourseOffering;
import edu.miu.attendifypro.repository.CourseOfferingRepository;
import edu.miu.attendifypro.service.persistence.CourseOfferingPersistenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseOfferingPersistenceServiceImpl implements CourseOfferingPersistenceService {

    private final CourseOfferingRepository courseOfferingRepository;

    public CourseOfferingPersistenceServiceImpl(CourseOfferingRepository courseOfferingRepository) {
        this.courseOfferingRepository = courseOfferingRepository;
    }

    @Override
    public List<CourseOffering> findAll() {
        return courseOfferingRepository.findAll();
    }

    @Override
    public Page<CourseOffering> findAll(Pageable pageable) {
        return courseOfferingRepository.findAll(pageable);
    }

    @Override
    public List<Course> getCoursesByDate(LocalDate date) {
        return courseOfferingRepository.getCoursesByDate(date);
    }

    @Override
    public Optional<CourseOffering> findById(Long id) {
        return courseOfferingRepository.findById(id);
    }

    @Override
    public CourseOffering save(CourseOffering courseOffering) {
        return courseOfferingRepository.save(courseOffering);
    }

    @Override
    public void delete(CourseOffering courseOffering) {
        courseOfferingRepository.delete(courseOffering);
    }

    @Override
    public CourseOffering getCourseOfferingById(long id) {
        return null;
    }

//    @Override
//    public CourseOffering getCourseOfferingById(long id) {
//        return courseOfferingRepository.getCourseOfferingById(id);
//    }
}
