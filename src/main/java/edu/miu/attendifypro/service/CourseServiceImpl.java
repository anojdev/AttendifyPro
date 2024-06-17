package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.request.CourseCreateRequest;
import edu.miu.attendifypro.dto.request.CourseUpdateRequest;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.mapper.DtoMapper;
import edu.miu.attendifypro.service.persistence.CoursePersistenceService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService{

    private final CoursePersistenceService persistenceService;

    public CourseServiceImpl(CoursePersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }


    @Override
    public ServiceResponse<List<CourseResponse>> getAllCourses() {
        try {
            List<Course> courseList = persistenceService.findAll();
            List<CourseResponse> courseResponses =courseList.stream().map(DtoMapper.dtoMapper::courseToCourseDto).toList();
            return ServiceResponse.of(courseResponses, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

    public ServiceResponse<CourseResponse> getCourse(Long id) {
        try {
            Optional<Course> courseOpt = persistenceService.findById(id);
            if(courseOpt.isPresent()) {
                return ServiceResponse.of(DtoMapper.dtoMapper.courseToCourseDto(courseOpt.get()), AppStatusCode.S20005);
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
    public ServiceResponse<Page<CourseResponse>> getCoursePage(Pageable pageable) {
        try {
            Page<Course> coursePage = persistenceService.findAll(pageable);
            Page<CourseResponse> courseDtoPage = coursePage.map(DtoMapper.dtoMapper::courseToCourseDto);
            return ServiceResponse.of(courseDtoPage, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

    @Override
    public ServiceResponse<CourseResponse> createCourse(CourseCreateRequest courseCreateRequest) {
        try {
            Optional<Course> existingCourse=persistenceService.findByCourseCode(courseCreateRequest.getCourseCode());
            if(existingCourse.isPresent()){
                return ServiceResponse.of(AppStatusCode.E40006,List.of("course.code.exists"));
            }

            List<Course> prerequisiteCourses=new ArrayList<>();
            if(Objects.nonNull(courseCreateRequest.getPrerequisites()) && !courseCreateRequest.getPrerequisites().isEmpty()){
                HashSet<Long> uniqueRequisiteIds=new HashSet<>(courseCreateRequest.getPrerequisites());
                prerequisiteCourses=persistenceService.findAllById(uniqueRequisiteIds);
                if(uniqueRequisiteIds.size()!=prerequisiteCourses.size()){
                    return ServiceResponse.of(AppStatusCode.E40002,List.of("invalid.prerequisite.code"));
                }
            }

            Course course = DtoMapper.dtoMapper.courseCreateRequestToCourse(courseCreateRequest);
            if(!prerequisiteCourses.isEmpty()) {
                course.setPrerequisites(prerequisiteCourses);
            }
            persistenceService.save(course);
            return ServiceResponse.of(DtoMapper.dtoMapper.courseToCourseDto(course),AppStatusCode.S20001);

        }catch(DataIntegrityViolationException ex){
            return ServiceResponse.of(AppStatusCode.E40002);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50003);
        }
    }

    @Override
    public ServiceResponse<CourseResponse> updateCourse(Long id, CourseUpdateRequest updateReq) {
        Optional<Course> courseOpt=persistenceService.findById(id);
        if(courseOpt.isPresent()){
            List<Course> prerequisites=new ArrayList<>();
            if(Objects.nonNull(updateReq.getPrerequisites()) && !updateReq.getPrerequisites().isEmpty() ){
                HashSet<Long> uniqueRequisiteIds=new HashSet<>(updateReq.getPrerequisites());
                prerequisites=persistenceService.findAllById(uniqueRequisiteIds);
                if(uniqueRequisiteIds.size()!=prerequisites.size()){
                    //TODO messaging
                    return ServiceResponse.of(AppStatusCode.E40002);
                }
            }

            try {
                Course course = courseOpt.get();
                course.setCourseCode(updateReq.getCourseCode());
                course.setCourseName(updateReq.getCourseName());
                course.setCourseDescription(updateReq.getCourseDescription());
                course.setDepartment(updateReq.getCourseDescription());
                if(!prerequisites.isEmpty()) {
                    course.setPrerequisites(prerequisites);
                }
                persistenceService.save(course);
                return ServiceResponse.of(DtoMapper.dtoMapper.courseToCourseDto(course),AppStatusCode.S20002);
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
    public ServiceResponse<Boolean> deleteCourse(Long id) {
        try {
            Optional<Course> courseOpt = persistenceService.findById(id);
            if(courseOpt.isPresent()) {
                persistenceService.delete(courseOpt.get());
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
