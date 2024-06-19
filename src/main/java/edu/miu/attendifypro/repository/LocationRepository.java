package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kush
 */
public interface LocationRepository extends JpaRepository<Location,Long> {
}
