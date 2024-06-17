package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.Location;
import edu.miu.attendifypro.repository.LocationRepository;
import edu.miu.attendifypro.service.persistence.LocationPersistenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class LocationPersistenceServiceImpl implements LocationPersistenceService {

    private final LocationRepository locationRepository;


    public LocationPersistenceServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Page<Location> findAll(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @Override
    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }


    @Override
    public List<Location> findAllById(HashSet<Long> uniqueRequisiteIds) {
        return locationRepository.findAllById(uniqueRequisiteIds);
    }

    @Override
    public Location save(Location course) {
        return locationRepository.save(course);
    }

    @Override
    public void delete(Location course) {
        locationRepository.delete(course);
    }
}
