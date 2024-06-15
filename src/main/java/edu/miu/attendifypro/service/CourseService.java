package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.CourseCreateRequest;
import edu.miu.attendifypro.dto.CourseDto;
import edu.miu.attendifypro.dto.CourseUpdateRequest;
import edu.miu.attendifypro.dto.ServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
    ServiceResponse<CourseDto> getAccount(Long id);

    ServiceResponse<Page<CourseDto>> getCoursePage(Pageable pageable);

    ServiceResponse<CourseDto> createCourse(CourseCreateRequest courseCreateRequest);

    ServiceResponse<CourseDto> updateCourse(Long id,CourseUpdateRequest courseUpdateRequest);

    ServiceResponse<Boolean> deleteCourse(Long id);

    ServiceResponse<List<CourseDto>> getAllCourses();
}
