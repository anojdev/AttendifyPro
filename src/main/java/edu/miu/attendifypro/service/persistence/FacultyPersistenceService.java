package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FacultyPersistenceService {
    List<Faculty> findAll();

    Page<Faculty> findAll(Pageable pageable);

    Optional<Faculty> findById(Long id);

    Faculty save(Faculty faculty);

    void delete(Faculty faculty);
}
