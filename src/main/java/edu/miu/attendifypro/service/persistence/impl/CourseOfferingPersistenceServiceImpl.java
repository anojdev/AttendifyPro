package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.CourseOffering;
import edu.miu.attendifypro.domain.LocationType;
import edu.miu.attendifypro.repository.CourseOfferingRepository;
import edu.miu.attendifypro.repository.LocationTypeRepository;
import edu.miu.attendifypro.service.persistence.CourseOfferingPersistenceService;
import edu.miu.attendifypro.service.persistence.LocationTypePersistenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author kush
 */
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
    public Optional<CourseOffering> findById(Long id) {
        return courseOfferingRepository.findById(id);
    }


}
