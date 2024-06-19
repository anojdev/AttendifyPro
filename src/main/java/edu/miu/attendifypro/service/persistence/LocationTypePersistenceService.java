package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.LocationType;

import java.util.List;
import java.util.Optional;

/**
 * @author kush
 */
public interface LocationTypePersistenceService {
    List<LocationType> findAll();

    Optional<LocationType> findById(Long id);

    Optional<LocationType> findByType(String type);
}
