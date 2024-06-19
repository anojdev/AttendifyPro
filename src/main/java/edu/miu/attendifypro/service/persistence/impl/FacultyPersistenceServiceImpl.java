package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.Faculty;
import edu.miu.attendifypro.repository.FacultyRepository;
import edu.miu.attendifypro.service.persistence.FacultyPersistenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FacultyPersistenceServiceImpl implements FacultyPersistenceService {
    private final FacultyRepository facultyRepository;

    public FacultyPersistenceServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Page<Faculty> findAll(Pageable pageable) {
        return facultyRepository.findAll(pageable);
    }

    @Override
    public Optional<Faculty> findById(Long id) {
        return facultyRepository.findById(id);
    }

    @Override
    public Faculty save(Faculty course) {
        return facultyRepository.save(course);
    }

    @Override
    public void delete(Faculty course) {
        facultyRepository.delete(course);
    }
}
