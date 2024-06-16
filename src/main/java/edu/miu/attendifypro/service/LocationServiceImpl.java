package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.domain.Location;
import edu.miu.attendifypro.domain.LocationType;
import edu.miu.attendifypro.dto.request.LocationCreateRequest;
import edu.miu.attendifypro.dto.request.LocationUpdateRequest;
import edu.miu.attendifypro.dto.response.LocationResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.mapper.LocationDtoMapper;
import edu.miu.attendifypro.service.persistence.LocationPersistenceService;
import edu.miu.attendifypro.service.persistence.LocationTypePersistenceService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService{

    private final LocationPersistenceService persistenceService;
    private final LocationTypePersistenceService locationTypePersistenceService;

    public LocationServiceImpl(LocationPersistenceService persistenceService, LocationTypePersistenceService locationTypePersistenceService) {
        this.persistenceService = persistenceService;
        this.locationTypePersistenceService = locationTypePersistenceService;
    }


    @Override
    public ServiceResponse<List<LocationResponse>> getAllLocations() {
        try {
            List<Location> locationList = persistenceService.findAll();
            List<LocationResponse> locationResponses =locationList.stream().map(LocationDtoMapper.locationDtoMapper::locationToLocationDto).toList();
            return ServiceResponse.of(locationResponses, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

    @Override
    public ServiceResponse<LocationResponse> getLocation(Long id) {
        try {
            Optional<Location> locationOpt = persistenceService.findById(id);
            if(locationOpt.isPresent()) {
                return ServiceResponse.of(LocationDtoMapper.locationDtoMapper.locationToLocationDto(locationOpt.get()), AppStatusCode.S20005);
            }
            else{
                return ServiceResponse.of(AppStatusCode.E40004);
            }
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50001);
        }
    }

    @Override
    public ServiceResponse<Page<LocationResponse>> getLocationPage(Pageable pageable) {
        try {
            Page<Location> locationPage = persistenceService.findAll(pageable);
            Page<LocationResponse> locationDtoPage = locationPage.map(LocationDtoMapper.locationDtoMapper::locationToLocationDto);
            return ServiceResponse.of(locationDtoPage, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

    @Override
    public ServiceResponse<LocationResponse> createLocation(LocationCreateRequest locationCreateRequest) {
        try {

            Optional<LocationType> locationTypeOpt=locationTypePersistenceService.findById(locationCreateRequest.getLocationType());
            if(locationTypeOpt.isEmpty()){
                return ServiceResponse.of(AppStatusCode.E40002,List.of("bad.values.supplied"));
            }

            Location location = LocationDtoMapper.locationDtoMapper.locationCreateRequestToLocation(locationCreateRequest);
            location.setLocationType(locationTypeOpt.get());
            persistenceService.save(location);
            return ServiceResponse.of(LocationDtoMapper.locationDtoMapper.locationToLocationDto(location),AppStatusCode.S20001);

        }catch(DataIntegrityViolationException ex){
            return ServiceResponse.of(AppStatusCode.E40002);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50003);
        }
    }

    @Override
    public ServiceResponse<LocationResponse> updateLocation(Long id, LocationUpdateRequest updateReq) {
        Optional<Location> locationOpt=persistenceService.findById(id);
        if(locationOpt.isPresent()){
            try {
                Location location = locationOpt.get();
                if(Objects.nonNull(updateReq.getLocationType())) {
                    Optional<LocationType> locationTypeOpt = locationTypePersistenceService.findById(updateReq.getLocationType());
                    if (locationTypeOpt.isEmpty()) {
                        return ServiceResponse.of(AppStatusCode.E40002, List.of("bad.values.supplied"));
                    }
                    else{
                        location.setLocationType(locationTypeOpt.get());
                    }
                }
                location.setName(updateReq.getName());
                location.setCapacity(updateReq.getCapacity());

                persistenceService.save(location);
                return ServiceResponse.of(LocationDtoMapper.locationDtoMapper.locationToLocationDto(location),AppStatusCode.S20002);
            }
            catch (Exception e){
                return ServiceResponse.of(AppStatusCode.E50002);
            }
        }
        else {
            return ServiceResponse.of(AppStatusCode.E40004);
        }
    }

    @Override
    public ServiceResponse<Boolean> deleteLocation(Long id) {
        try {
            Optional<Location> locationOpt = persistenceService.findById(id);
            if(locationOpt.isPresent()) {
                persistenceService.delete(locationOpt.get());
                return ServiceResponse.of(true,AppStatusCode.S20003);
            }
            else{
                return ServiceResponse.of(AppStatusCode.E40004);
            }
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50005);
        }
    }

}
