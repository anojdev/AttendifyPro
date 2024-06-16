package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.domain.Location;
import edu.miu.attendifypro.domain.LocationType;
import edu.miu.attendifypro.dto.request.CourseCreateRequest;
import edu.miu.attendifypro.dto.request.CourseUpdateRequest;
import edu.miu.attendifypro.dto.request.LocationCreateRequest;
import edu.miu.attendifypro.dto.request.LocationUpdateRequest;
import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.response.LocationResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.mapper.DtoMapper;
import edu.miu.attendifypro.mapper.LocationDtoMapper;
import edu.miu.attendifypro.service.persistence.CoursePersistenceService;
import edu.miu.attendifypro.service.persistence.LocationPersistenceService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocationServiceImpl implements LocationService{

    private final LocationPersistenceService persistenceService;

    public LocationServiceImpl(LocationPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }


    @Override
    public ServiceResponse<List<LocationResponse>> getAllLocations() {
        try {
            List<Location> locationList = persistenceService.findAll();
            List<LocationResponse> locationResponses =locationList.stream().map(LocationDtoMapper.locationdtoMapper::locationToLocationDto).toList();
            return ServiceResponse.of(locationResponses, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

//    public ServiceResponse<LocationResponse> getAccount(Long id) {
//        try {
//            Optional<Location> locationOpt = persistenceService.findById(id);
//            if(locationOpt.isPresent()) {
//                return ServiceResponse.of(LocationDtoMapper.locationdtoMapper.locationToLocationDto(locationOpt.get()), AppStatusCode.S20005);
//            }
//            else{
//                return ServiceResponse.of(AppStatusCode.E40004);
//            }
//        }
//        catch (Exception e){
//            return ServiceResponse.of(AppStatusCode.E50001);
//        }
//    }

    @Override
    public ServiceResponse<Page<LocationResponse>> getLocationPage(Pageable pageable) {
        try {
            Page<Location> coursePage = persistenceService.findAll(pageable);
            Page<LocationResponse> courseDtoPage = coursePage.map(LocationDtoMapper.locationdtoMapper::locationToLocationDto);
            return ServiceResponse.of(courseDtoPage, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

    @Override
    public ServiceResponse<LocationResponse> createLocation(LocationCreateRequest locationCreateRequest) {
        try {
//            Optional<Location> existingCourse=persistenceService.findByCourseCode(courseCreateRequest.getCourseCode());
//            if(existingCourse.isPresent()){
//                return ServiceResponse.of(AppStatusCode.E40006,List.of("course.code.exists"));
//            }
//
//            List<Course> prerequisiteCourses=new ArrayList<>();
//            if(Objects.nonNull(courseCreateRequest.getPrerequisites()) && !courseCreateRequest.getPrerequisites().isEmpty()){
//                HashSet<Long> uniqueRequisiteIds=new HashSet<>(courseCreateRequest.getPrerequisites());
//                prerequisiteCourses=persistenceService.findAllById(uniqueRequisiteIds);
//                if(uniqueRequisiteIds.size()!=prerequisiteCourses.size()){
//                    return ServiceResponse.of(AppStatusCode.E40002,List.of("invalid.prerequisite.code"));
//                }
//            }

            Location location = LocationDtoMapper.locationdtoMapper.locationCreateRequestToLocation(locationCreateRequest);
//            if(!prerequisiteCourses.isEmpty()) {
//                course.setPrerequisites(prerequisiteCourses);
//            }
            persistenceService.save(location);
            return ServiceResponse.of(LocationDtoMapper.locationdtoMapper.locationToLocationDto(location),AppStatusCode.S20001);

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
           // List<LocationType> prerequisites=new ArrayList<>();
            try {
                Location location = locationOpt.get();
                location.setName(updateReq.getName());
                location.setCapacity(updateReq.getCapacity());
                location.setLocationType(updateReq.getLocationType());
//                if(!prerequisites.isEmpty()) {
//                    location.setPrerequisites(prerequisites);
//                }
                persistenceService.save(location);
                return ServiceResponse.of(LocationDtoMapper.locationdtoMapper.locationToLocationDto(location),AppStatusCode.S20002);
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
