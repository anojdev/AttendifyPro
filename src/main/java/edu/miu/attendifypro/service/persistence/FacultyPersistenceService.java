package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.Faculty;

import java.util.Optional;

/**
 * @author kush
 */
public interface FacultyPersistenceService {
    Optional<Faculty> findById(Long id);
}
