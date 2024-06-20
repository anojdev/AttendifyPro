package edu.miu.attendifypro.controller;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import edu.miu.attendifypro.controller.sysadmin.CourseOfferingController;
import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.dto.request.CourseOfferingCreateRequest;
import edu.miu.attendifypro.dto.request.CourseOfferingUpdateRequest;
import edu.miu.attendifypro.dto.response.*;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.dto.response.report.CourseOfferingReport1Response;
import edu.miu.attendifypro.dto.response.report.CourseReportResponse;
import edu.miu.attendifypro.dto.response.report.Report1Response;
import edu.miu.attendifypro.service.CourseOfferingService;
import edu.miu.attendifypro.service.MessagingService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CourseOfferingControllerTest {

    @Mock
    private CourseOfferingService courseOfferingService;

    @Mock
    private MessagingService messagingService;

    @InjectMocks
    private CourseOfferingController courseOfferingController;

    private CourseOfferingCreateRequest createRequest;
    private CourseOfferingUpdateRequest updateRequest;
    private List<CourseOfferingResponse> courseOfferingResponses;
    private CourseOfferingResponse courseOfferingResponse;

    private final Long courseId = 1L;
    private List<Report1Response> reportResponses;
    @BeforeEach
    void setUp() {
        LocationResponse location = new LocationResponse(1L,200,"Building A", new LocationTypeDto(1L, "Lecture Hall"));

        FacultyResponse faculty = new FacultyResponse(1L,
                "John",
                "Doe",
                LocalDate.of(1980, 1, 1),
                "john.doe@example.com",
                "Male",
                "salute1","Dr. John Doe"
        );
        CourseResponse course = new CourseResponse(1L, 4, "CS401", "Advanced Software Engineering", "Course Description", "Computer Science",null);

        courseOfferingResponses = Arrays.asList(
                new CourseOfferingResponse(1L, 30, "Lecture", location, LocalDate.of(2024, 1, 15), LocalDate.of(2024, 5, 15), faculty, course),
                new CourseOfferingResponse(2L, 25, "Lab", location, LocalDate.of(2024, 1, 15), LocalDate.of(2024, 5, 15), faculty, course)
        );

        courseOfferingResponse = new CourseOfferingResponse(1L, 30, "Lecture", location, LocalDate.of(2024, 1, 15), LocalDate.of(2024, 5, 15), faculty, course);
        createRequest = new CourseOfferingCreateRequest(30, "Lecture", 1L, LocalDate.of(2024, 1, 15), LocalDate.of(2024, 5, 15), 1L, 1L);
        updateRequest = new CourseOfferingUpdateRequest(30, "Lecture", 1L, LocalDate.of(2024, 1, 15), LocalDate.of(2024, 5, 15), 1L, 1L);

        reportResponses = Arrays.asList(
                new Report1Response(
                        new CourseOfferingReport1Response(
                                LocalDate.of(2023, 1, 10),
                                LocalDate.of(2023, 5, 20),
                                new CourseReportResponse(3, "CS101", "Intro to Computer Science")
                        ),
                        "A"
                ),
                new Report1Response(
                        new CourseOfferingReport1Response(
                                LocalDate.of(2023, 1, 10),
                                LocalDate.of(2023, 5, 20),
                                new CourseReportResponse(4, "CS201", "Data Structures")
                        ),
                        "B"
                )
        );
    }

    @Test
    void getAllCourseOffering_ShouldReturnAllCourseOfferings_WhenDataIsPresent() {
        ServiceResponse<List<CourseOfferingResponse>> serviceResponse = new ServiceResponse<>(Optional.of(courseOfferingResponses),
                AppStatusCode.S20000,new HashMap<>());

        when(courseOfferingService.getAllCourseOfferings()).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Success");

        ResponseEntity<ApiResponse<List<CourseOfferingResponse>>> response = courseOfferingController.getAllCourseOffering();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isStatus()).isTrue();
        assertThat(response.getBody().getData()).isEqualTo(courseOfferingResponses);
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
    }

    @Test
    void getAllCourseOffering_ShouldReturnNoCourseOfferings_WhenDataIsNotPresent() {
        ServiceResponse<List<CourseOfferingResponse>> serviceResponse = new ServiceResponse<>(Optional.empty(),
                AppStatusCode.E40000,new HashMap<>());

        when(courseOfferingService.getAllCourseOfferings()).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("No Course Offerings found");

        ResponseEntity<ApiResponse<List<CourseOfferingResponse>>> response = courseOfferingController.getAllCourseOffering();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().isStatus()).isFalse();
        assertThat(response.getBody().getData()).isNull();
        assertThat(response.getBody().getMessage()).isEqualTo("No Course Offerings found");
    }

    @Test
    void getCourseOfferingById_ShouldReturnCourseOffering_WhenIdIsValid() {
        ServiceResponse<CourseOfferingResponse> serviceResponse = new ServiceResponse<>(
                Optional.of(courseOfferingResponse), AppStatusCode.S20000, new HashMap<>()
        );

        when(courseOfferingService.getCourseOfferingById(1L)).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Success");

        ResponseEntity<ApiResponse<CourseOfferingResponse>> response = courseOfferingController.getCourseOfferingById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isStatus()).isTrue();
        assertThat(response.getBody().getData()).isEqualTo(courseOfferingResponse);
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
    }

    @Test
    void getCourseOfferingById_ShouldReturnNotFound_WhenIdIsInvalid() {
        ServiceResponse<CourseOfferingResponse> serviceResponse = new ServiceResponse<>(
                Optional.empty(), AppStatusCode.E40000, new HashMap<>()
        );

        when(courseOfferingService.getCourseOfferingById(2L)).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Course Offering not found");

        ResponseEntity<ApiResponse<CourseOfferingResponse>> response = courseOfferingController.getCourseOfferingById(2L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().isStatus()).isFalse();
        assertThat(response.getBody().getData()).isNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Course Offering not found");
    }

    @Test
    void create_ShouldReturnCreatedCourseOffering_WhenRequestIsValid() {
        ServiceResponse<CourseOfferingResponse> serviceResponse = new ServiceResponse<>(
                Optional.of(courseOfferingResponse), AppStatusCode.S20001, new HashMap<>()
        );

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(courseOfferingService.createCourseOffering(createRequest)).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Success");

        ResponseEntity<ApiResponse<CourseOfferingResponse>> response = courseOfferingController.create(createRequest, httpServletRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().isStatus()).isTrue();
        assertThat(response.getBody().getData()).isEqualTo(courseOfferingResponse);
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
    }

    @Test
    void create_ShouldReturnBadRequest_WhenRequestIsInvalid() {
        ServiceResponse<CourseOfferingResponse> serviceResponse = new ServiceResponse<>(
                Optional.empty(), AppStatusCode.E40000, new HashMap<>()
        );

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(courseOfferingService.createCourseOffering(createRequest)).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Invalid course offering request");

        ResponseEntity<ApiResponse<CourseOfferingResponse>> response = courseOfferingController.create(createRequest, httpServletRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().isStatus()).isFalse();
        assertThat(response.getBody().getData()).isNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Invalid course offering request");
    }

    @Test
    void update_ShouldReturnUpdatedCourseOffering_WhenRequestIsValid() {
        ServiceResponse<CourseOfferingResponse> serviceResponse = new ServiceResponse<>(
                Optional.of(courseOfferingResponse), AppStatusCode.S20002, new HashMap<>()
        );

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(courseOfferingService.updateCourseOffering(1L, updateRequest)).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Success");

        ResponseEntity<ApiResponse<CourseOfferingResponse>> response = courseOfferingController.update(updateRequest, 1L, httpServletRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isStatus()).isTrue();
        assertThat(response.getBody().getData()).isEqualTo(courseOfferingResponse);
        assertThat(response.getBody().getMessage()).isEqualTo("Success");
    }

    @Test
    void update_ShouldReturnBadRequest_WhenRequestIsInvalid() {
        ServiceResponse<CourseOfferingResponse> serviceResponse = new ServiceResponse<>(
                Optional.empty(), AppStatusCode.E40000, new HashMap<>()
        );

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(courseOfferingService.updateCourseOffering(1L, updateRequest)).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Invalid course offering update request");

        ResponseEntity<ApiResponse<CourseOfferingResponse>> response = courseOfferingController.update(updateRequest, 1L, httpServletRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().isStatus()).isFalse();
        assertThat(response.getBody().getData()).isNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Invalid course offering update request");
    }
    @Test
    void delete_ShouldReturnTrue_WhenCourseOfferingIsDeleted() {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>(
                Optional.of(true), AppStatusCode.S20003, new HashMap<>()
        );

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(courseOfferingService.deleteCourseOffering(courseId)).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Course offering deleted successfully");

        ResponseEntity<ApiResponse<Boolean>> response = courseOfferingController.delete(courseId, httpServletRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isStatus()).isTrue();
        assertThat(response.getBody().getData()).isTrue();
        assertThat(response.getBody().getMessage()).isEqualTo("Course offering deleted successfully");
    }

    @Test
    void delete_ShouldReturnFalse_WhenCourseOfferingIsNotDeleted() {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>(
                Optional.of(false), AppStatusCode.E40004, new HashMap<>()
        );

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(courseOfferingService.deleteCourseOffering(courseId)).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Failed to delete course offering");

        ResponseEntity<ApiResponse<Boolean>> response = courseOfferingController.delete(courseId, httpServletRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().isStatus()).isTrue();
        assertThat(response.getBody().getData()).isFalse();
        assertThat(response.getBody().getMessage()).isEqualTo("Failed to delete course offering");
    }

    @Test
    void delete_ShouldReturnNotFound_WhenCourseOfferingIsNotFound() {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>(
                Optional.empty(), AppStatusCode.E40004, new HashMap<>()
        );

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(courseOfferingService.deleteCourseOffering(courseId)).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Course offering not found");

        ResponseEntity<ApiResponse<Boolean>> response = courseOfferingController.delete(courseId, httpServletRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().isStatus()).isFalse();
        assertThat(response.getBody().getData()).isNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Course offering not found");
    }
}

