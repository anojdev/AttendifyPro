package edu.miu.attendifypro.controller.student;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentCourseOfferingControllerTest {
    @Mock
    private CourseOfferingService courseOfferingService;
    @Mock
    private MessagingService messagingService;
    private List<Report1Response> reportResponses;

    @InjectMocks
    private StudentCourseOfferingController courseOfferingController;

    private List<StudentCourseSelectionResponse> studentCourseSelectionResponses;
    @BeforeEach
    void setUp() {
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
    void getStudentCourseOffering_ShouldReturnListOfReport1Response_WhenDataIsPresent() {
        ServiceResponse<List<Report1Response>> serviceResponse = new ServiceResponse<>(
                Optional.of(reportResponses), AppStatusCode.S20000, new HashMap<>()
        );

        when(courseOfferingService.getStudentCourseOffering()).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Data fetched successfully");

        ResponseEntity<ApiResponse<List<Report1Response>>> response = courseOfferingController.getStudentCourseOffering();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isStatus()).isTrue();
        assertThat(response.getBody().getData()).isEqualTo(reportResponses);
        assertThat(response.getBody().getMessage()).isEqualTo("Data fetched successfully");


        CourseResponse course = new CourseResponse(1L, 3, "CS401",
                "Advanced Software Engineering",
                "A course about advanced software engineering concepts.",
                "Computer Science",null);
        FacultyResponse faculty = new FacultyResponse(1L,
                "John",
                "Doe",
                LocalDate.of(1980, 1, 1),
                "john.doe@example.com",
                "Male",
                "salute1","Dr. John Doe"
        );
        LocationResponse location = new LocationResponse(1L,200,
                "Building A", new LocationTypeDto(1L,
                "Lecture Hall"));

        CourseOfferingResponse courseOffering = new CourseOfferingResponse(
                1L,
                30,
                "Lecture",
                location,
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 12, 15),
                faculty,
                course
        );

        StudentResponse student1 = new StudentResponse(
                1L,
                LocalDate.of(2020, 1, 15),
                "S12345",
                "A12345",
                faculty,
                "Alice",
                "Smith",
                LocalDate.of(2002, 5, 20),
                "alice.smith@example.com",
                "Female",
                Arrays.asList(course)
        );

        StudentResponse student2 = new StudentResponse(
                2L,
                LocalDate.of(2019, 8, 20),
                "S54321",
                "A54321",
                faculty,
                "Bob",
                "Johnson",
                LocalDate.of(2001, 3, 15),
                "bob.johnson@example.com",
                "Male",
                Arrays.asList(course)
        );

        List<StudentCourseSelectionResponse> studentCourseSelectionResponses = Arrays.asList(
                new StudentCourseSelectionResponse(1L, courseOffering, student1, "A"),
                new StudentCourseSelectionResponse(2L, courseOffering, student2, "B")
        );
    }

    @Test
    void getStudentCourseOffering_ShouldReturnEmptyList_WhenNoDataIsPresent() {
        ServiceResponse<List<Report1Response>> serviceResponse = new ServiceResponse<>(
                Optional.empty(), AppStatusCode.S20004, new HashMap<>()
        );

        when(courseOfferingService.getStudentCourseOffering()).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("No data available");

        ResponseEntity<ApiResponse<List<Report1Response>>> response = courseOfferingController.getStudentCourseOffering();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isStatus()).isFalse();
        assertThat(response.getBody().getData()).isNull();
        assertThat(response.getBody().getMessage()).isEqualTo("No data available");
    }

    @Test
    void getStudentCourseOffering_ShouldHandleErrorResponse() {
        ServiceResponse<List<Report1Response>> serviceResponse = new ServiceResponse<>(
                Optional.empty(), AppStatusCode.E50001, new HashMap<>()
        );

        when(courseOfferingService.getStudentCourseOffering()).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Internal server error");

        ResponseEntity<ApiResponse<List<Report1Response>>> response = courseOfferingController.getStudentCourseOffering();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().isStatus()).isFalse();
        assertThat(response.getBody().getData()).isNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Internal server error");
    }

    @Test
    void getCourseOfferingById_ShouldReturnEmptyList_WhenNoDataIsPresent() {
        Long offeringId = 1L;
        ServiceResponse<List<StudentCourseSelectionResponse>> serviceResponse = new ServiceResponse<>(
                Optional.empty(), AppStatusCode.E50001, new HashMap<>()
        );

        when(courseOfferingService.getStudentCourseOfferingById(offeringId)).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("No data available");

        ResponseEntity<ApiResponse<List<StudentCourseSelectionResponse>>> response =
                courseOfferingController.getCourseOfferingById(offeringId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isStatus()).isFalse();
        assertThat(response.getBody().getData()).isNull();
        assertThat(response.getBody().getMessage()).isEqualTo("No data available");

    }
    @Test
    public void testGetCourseOfferingById_NotFound() {
        Long offeringId = 2L;
        ServiceResponse<List<StudentCourseSelectionResponse>> serviceResponse = new ServiceResponse<>(
                Optional.empty(), AppStatusCode.E50001, new HashMap<>());

        when(courseOfferingService.getStudentCourseOfferingById(offeringId)).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(serviceResponse, new String[]{"CourseOffering"}))
                .thenReturn("No data found");

        ResponseEntity<ApiResponse<List<StudentCourseSelectionResponse>>> response =
                courseOfferingController.getCourseOfferingById(offeringId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().isStatus()).isFalse();
        assertThat(response.getBody().getData()).isNull();
        assertThat(response.getBody().getMessage()).isEqualTo("No data available");
    }
}
