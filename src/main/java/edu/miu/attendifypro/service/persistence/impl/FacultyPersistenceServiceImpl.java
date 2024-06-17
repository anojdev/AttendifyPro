package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.Faculty;
import edu.miu.attendifypro.repository.FacultyRepository;
import edu.miu.attendifypro.service.persistence.FacultyPersistenceService;

import java.util.Optional;

/**
 * @author kush
 */

public class FacultyPersistenceServiceImpl implements FacultyPersistenceService {
    private final FacultyRepository facultyRepository;

    public FacultyPersistenceServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Optional<Faculty> findById(Long id) {
        return facultyRepository.findById(id);
    }
}
