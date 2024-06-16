package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author kush
 */
public interface LocationTypeRepository extends JpaRepository<LocationType,Long> {
    Optional<LocationType> findByType(String type);
}
