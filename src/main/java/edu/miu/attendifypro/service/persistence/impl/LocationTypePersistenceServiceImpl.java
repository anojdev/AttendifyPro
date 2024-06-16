package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.LocationType;
import edu.miu.attendifypro.repository.LocationTypeRepository;
import edu.miu.attendifypro.service.persistence.LocationTypePersistenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author kush
 */
@Service
@Transactional
public class LocationTypePersistenceServiceImpl implements LocationTypePersistenceService {

    private final LocationTypeRepository locationTypeRepository;


    public LocationTypePersistenceServiceImpl(LocationTypeRepository locationTypeRepository) {
        this.locationTypeRepository = locationTypeRepository;
    }

    @Override
    public List<LocationType> findAll() {
        return locationTypeRepository.findAll();
    }

    @Override
    public Optional<LocationType> findById(Long id) {
        return locationTypeRepository.findById(id);
    }
    @Override
    public Optional<LocationType> findByType(String type) {
        return locationTypeRepository.findByType(type);

    }


}
