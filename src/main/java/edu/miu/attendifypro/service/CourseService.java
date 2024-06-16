package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.request.CourseCreateRequest;
import edu.miu.attendifypro.dto.request.CourseUpdateRequest;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
    ServiceResponse<CourseResponse> getAccount(Long id);

    ServiceResponse<Page<CourseResponse>> getCoursePage(Pageable pageable);

    ServiceResponse<CourseResponse> createCourse(CourseCreateRequest courseCreateRequest);

    ServiceResponse<CourseResponse> updateCourse(Long id, CourseUpdateRequest courseUpdateRequest);

    ServiceResponse<Boolean> deleteCourse(Long id);

    ServiceResponse<List<CourseResponse>> getAllCourses();
}
