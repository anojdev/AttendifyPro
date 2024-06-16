package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.CourseOffering;

import java.util.List;
import java.util.Optional;

/**
 * @author kush
 */
public interface CourseOfferingPersistenceService {
    List<CourseOffering> findAll();

    Optional<CourseOffering> findById(Long id);

}
