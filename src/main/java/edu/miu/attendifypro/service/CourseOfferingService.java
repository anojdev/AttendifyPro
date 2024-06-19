package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.request.CourseCreateRequest;
import edu.miu.attendifypro.dto.request.CourseOfferingCreateRequest;
import edu.miu.attendifypro.dto.request.CourseOfferingUpdateRequest;
import edu.miu.attendifypro.dto.request.CourseUpdateRequest;
import edu.miu.attendifypro.dto.response.CourseOfferingResponse;
import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.response.StudentCourseSelectionResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface CourseOfferingService {
    ServiceResponse<Page<CourseOfferingResponse>> getCourseOfferingPage(Pageable pageable);

    ServiceResponse<List<CourseOfferingResponse>> getAllCourseOfferings();
    ServiceResponse<CourseOfferingResponse> getCourseOfferingById(long id);
    ServiceResponse<List<CourseOfferingResponse>> filterCourseOffering(String date);

    ServiceResponse<CourseOfferingResponse> createCourseOffering(CourseOfferingCreateRequest createRequest);

    ServiceResponse<CourseOfferingResponse> updateCourseOffering(long id, CourseOfferingUpdateRequest updateRequest);

    ServiceResponse<Boolean> deleteCourseOffering(Long id);

    ServiceResponse<List<StudentCourseSelectionResponse>>  getStudentCourseOfferingById(long offeringId);
    ServiceResponse<List<StudentCourseSelectionResponse>>  getStudentCourseOffering();
}
