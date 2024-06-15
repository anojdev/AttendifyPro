package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.dto.CourseCreateRequest;
import edu.miu.attendifypro.dto.CourseDto;
import edu.miu.attendifypro.dto.CourseUpdateRequest;
import edu.miu.attendifypro.dto.ServiceResponse;
import edu.miu.attendifypro.mapper.CourseMapper;
import edu.miu.attendifypro.mapper.DtoMapper;
import edu.miu.attendifypro.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    //TODO Prerequisite check and handling
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseMapper courseMapper, CourseRepository courseRepository) {
        this.courseMapper = courseMapper;
        this.courseRepository = courseRepository;
    }


    @Override
    public ServiceResponse<List<CourseDto>> getAllCourses() {
        try {
            List<Course> courseList = courseRepository.findAll();
            List<CourseDto> courseDtos=courseList.stream().map(DtoMapper.dtoMapper::courseToCourseDto).toList();
            return ServiceResponse.of(courseDtos, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

    public ServiceResponse<CourseDto> getAccount(Long id) {
        try {
            Optional<Course> courseOpt = courseRepository.findById(id);
            if(courseOpt.isPresent()) {
                return ServiceResponse.of(courseMapper.entityToDto(courseOpt.get()), AppStatusCode.S20005);
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
    public ServiceResponse<Page<CourseDto>> getCoursePage(Pageable pageable) {
        try {
            Page<Course> coursePage = courseRepository.findAll(pageable);
            Page<CourseDto> courseDtoPage = coursePage.map(DtoMapper.dtoMapper::courseToCourseDto);
            return ServiceResponse.of(courseDtoPage, AppStatusCode.S20000);
        }
        catch (Exception e){
            return ServiceResponse.of(AppStatusCode.E50002);
        }
    }

    @Override
    public ServiceResponse<CourseDto> createCourse(CourseCreateRequest courseCreateRequest) {
        try {
            Course course = DtoMapper.dtoMapper.courseCreateRequestToCourse(courseCreateRequest);
            courseRepository.save(course);
            return ServiceResponse.of(DtoMapper.dtoMapper.courseToCourseDto(course),AppStatusCode.S20001);
        }
        catch(Exception e){
            return ServiceResponse.of(AppStatusCode.E50003);
        }
    }

    @Override
    public ServiceResponse<CourseDto> updateCourse(Long id,CourseUpdateRequest updateReq) {
        Optional<Course> courseOpt=courseRepository.findById(id);
        if(courseOpt.isPresent()){
            try {
                Course course = courseOpt.get();
                course.setCourseCode(updateReq.getCourseCode());
                course.setCourseName(updateReq.getCourseName());
                course.setCourseDescription(updateReq.getCourseDescription());
                course.setDepartment(updateReq.getCourseDescription());
                courseRepository.save(course);
                return ServiceResponse.of(DtoMapper.dtoMapper.courseToCourseDto(course),AppStatusCode.S20002);

            }catch (Exception e){
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
            Optional<Course> courseOpt = courseRepository.findById(id);
            if(courseOpt.isPresent()) {
                courseRepository.delete(courseOpt.get());
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
