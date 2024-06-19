package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface LocationPersistenceService {
    List<Location> findAll();

    Page<Location> findAll(Pageable pageable);

    Optional<Location> findById(Long id);

    Location save(Location location);

    void delete(Location location);
}
