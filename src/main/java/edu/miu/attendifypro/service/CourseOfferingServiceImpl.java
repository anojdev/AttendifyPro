package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.*;
import edu.miu.attendifypro.dto.request.CourseOfferingCreateRequest;
import edu.miu.attendifypro.dto.request.CourseOfferingUpdateRequest;
import edu.miu.attendifypro.dto.response.CourseOfferingResponse;
import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.mapper.CourseOfferingDtoMapper;
import edu.miu.attendifypro.mapper.DtoMapper;
import edu.miu.attendifypro.service.persistence.CourseOfferingPersistenceService;
import edu.miu.attendifypro.service.persistence.CoursePersistenceService;
import edu.miu.attendifypro.service.persistence.FacultyPersistenceService;
import edu.miu.attendifypro.service.persistence.LocationPersistenceService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseOfferingServiceImpl implements CourseOfferingService{

    private final CourseOfferingPersistenceService persistenceService;
    private final CoursePersistenceService coursePersistenceService;
    private final FacultyPersistenceService facultyPersistenceService;
    private final LocationPersistenceService locationPersistenceService;

    public CourseOfferingServiceImpl(CourseOfferingPersistenceService persistenceService,
                                     CoursePersistenceService coursePersistenceService,
                                     FacultyPersistenceService facultyPersistenceService,
                                     LocationPersistenceService locationPersistenceService) {
        this.persistenceService = persistenceService;
        this.coursePersistenceService = coursePersistenceService;
        this.facultyPersistenceService = facultyPersistenceService;
        this.locationPersistenceService = locationPersistenceService;
    }

    @Override
    public ServiceResponse<Page<CourseOfferingResponse>> getCourseOfferingPage(Pageable pageable) {
        try {
            Page<CourseOffering> courseOfferingPage = persistenceService.findAll(pageable);
            Page<CourseOfferingResponse> responsePage = courseOfferingPage
                                                    .map(CourseOfferingDtoMapper.courseOfferingDtoMapper
                                                    ::courseOfferingToCourseOfferingResponse);

            return ServiceResponse.of(responsePage, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

    @Override
    public ServiceResponse<List<CourseOfferingResponse>> getAllCourseOfferings() {
        try {
            List<CourseOffering> lst = persistenceService.findAll();
            List<CourseOfferingResponse> responseList =lst.stream()
                                            .map(CourseOfferingDtoMapper.courseOfferingDtoMapper
                                            ::courseOfferingToCourseOfferingResponse).toList();
            return ServiceResponse.of(responseList, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

    @Override
    public ServiceResponse<CourseOfferingResponse> createCourseOffering(CourseOfferingCreateRequest createRequest) {
        try {
            CourseOffering courseOffering = CourseOfferingDtoMapper.courseOfferingDtoMapper
                                    .courseOfferingCreateRequestToCourseOffering(createRequest);

            Optional<Course> course=coursePersistenceService.findById(createRequest.getCourseId());
            if(course.isEmpty()){
                return ServiceResponse.of(AppStatusCode.E40006,List.of("course.not.exists"));
            }
            courseOffering.setCourses(course.get());

            Optional<Location> location=locationPersistenceService.findById(createRequest.getLocationId());
            if(location.isEmpty()){
                return ServiceResponse.of(AppStatusCode.E40006,List.of("location.not.exists"));
            }
            courseOffering.setLocation(location.get());

            Optional<Faculty> faculty=facultyPersistenceService.findById(createRequest.getFacultyId());
            if(faculty.isEmpty()){
                return ServiceResponse.of(AppStatusCode.E40006,List.of("faculty.not.exists"));
            }
            courseOffering.setFaculty(faculty.get());
            persistenceService.save(courseOffering);

            return ServiceResponse.of(CourseOfferingDtoMapper.courseOfferingDtoMapper
                    .courseOfferingToCourseOfferingResponse(courseOffering),AppStatusCode.S20001);

        }catch(DataIntegrityViolationException ex){
            return ServiceResponse.of(AppStatusCode.E40002);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50003);
        }
    }

    @Override
    public ServiceResponse<CourseOfferingResponse> updateCourseOffering(long id,CourseOfferingUpdateRequest updateRequest) {
        try {
            Optional<CourseOffering> courseOfferingById=persistenceService.findById(id);
            if(courseOfferingById.isEmpty()){
                return ServiceResponse.of(AppStatusCode.E40006,List.of("not.found","Course Offering"));
            }
            CourseOffering courseOffering=courseOfferingById.get();
            courseOffering.setCapacity(updateRequest.getCapacity());
            courseOffering.setCourseOfferingType(updateRequest.getCourseOfferingType());
            courseOffering.setStartDate(updateRequest.getStartDate());
            courseOffering.setEndDate(updateRequest.getEndDate());

            Optional<Course> course=coursePersistenceService.findById(updateRequest.getCourseId());
            if(course.isEmpty()){
                return ServiceResponse.of(AppStatusCode.E40006,List.of("course.not.exists"));
            }
            courseOffering.setCourses(course.get());

            Optional<Location> location=locationPersistenceService.findById(updateRequest.getLocationId());
            if(location.isEmpty()){
                return ServiceResponse.of(AppStatusCode.E40006,List.of("location.not.exists"));
            }
            courseOffering.setLocation(location.get());

            Optional<Faculty> faculty=facultyPersistenceService.findById(updateRequest.getFacultyId());
            if(faculty.isEmpty()){
                return ServiceResponse.of(AppStatusCode.E40006,List.of("faculty.not.exists"));
            }
            courseOffering.setFaculty(faculty.get());
            persistenceService.save(courseOffering);

            return ServiceResponse.of(CourseOfferingDtoMapper.courseOfferingDtoMapper
                    .courseOfferingToCourseOfferingResponse(courseOffering),AppStatusCode.S20001);

        }catch(DataIntegrityViolationException ex){
            return ServiceResponse.of(AppStatusCode.E40002);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50003);
        }
    }

    @Override
    public ServiceResponse<Boolean> deleteCourseOffering(Long id) {
        try {
            Optional<CourseOffering> courseOfferingById=persistenceService.findById(id);
            if(courseOfferingById.isEmpty()){
                return ServiceResponse.of(AppStatusCode.E40006,List.of("not.found","Course Offering"));
            }
            persistenceService.delete(courseOfferingById.get());
            return ServiceResponse.of(true,AppStatusCode.S20001);

        }catch(DataIntegrityViolationException ex){
            return ServiceResponse.of(AppStatusCode.E40002);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50003);
        }
    }
}
