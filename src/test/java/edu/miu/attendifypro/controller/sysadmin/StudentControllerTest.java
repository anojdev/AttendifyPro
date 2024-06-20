package edu.miu.attendifypro.controller.sysadmin;

import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.dto.request.StudentRequest;
import edu.miu.attendifypro.dto.response.*;
import edu.miu.attendifypro.dto.response.common.ApiResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.MessagingService;
import edu.miu.attendifypro.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @Mock
    private MessagingService messagingService;

    private List<StudentResponse> studentResponse;


    @BeforeEach
    void setUp() {
        FacultyResponse facultyResponse =  new FacultyResponse(
                1L,
                "John",
                "Doe",
                LocalDate.of(1999, 8, 4),
                "john.doe@example.com",
                "Male",
                "Prof",
                "John Doe" );
        studentResponse = Arrays.asList(
                new StudentResponse (
                        1L,
                        LocalDate.of(2022, 8, 1),
                        "1001",
                        "D9234",
                        facultyResponse,
                        "Gladys",
                        "Kulhman",
                        LocalDate.of(2022, 6, 11),
                        "gladys.kulhman@example.com",
                        "Female",
       null
                ),
                new StudentResponse(
                        2L,
                        LocalDate.of(2022, 8, 1),
                        "1002",
                        "F988",
                        facultyResponse,
                        "Truman",
                        "Harriet",
                        LocalDate.of(2023, 6, 11),
                        "truman.harriet@example.com",
                        "Female",
                        null
                )
        );
    }

    @Test
    void testGetStudents_Success() {
        ServiceResponse<List<StudentResponse>> serviceResponse = new ServiceResponse<>(
                Optional.of(studentResponse), AppStatusCode.S20000, new HashMap<>()
        );

        when(studentService.getAllStudents()).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Data fetched successfully");

        ResponseEntity<ApiResponse<List<StudentResponse>>> response = studentController.getAllStudents();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isStatus()).isTrue();
        assertThat(response.getBody().getData()).isEqualTo(studentResponse);
        assertThat(response.getBody().getMessage()).isEqualTo("Data fetched successfully");


        FacultyResponse facultyResponse =  new FacultyResponse(
                1L,
                "John",
                "Doe",
                LocalDate.of(1999, 8, 4),
                "john.doe@example.com",
                "Male",
                "Prof",
                "John Doe" );

        StudentResponse student1 = new StudentResponse(
                1L,
                LocalDate.of(2022, 8, 1),
                "1001",
                "D9234",
                facultyResponse,
                "Gladys",
                "Kulhman",
                LocalDate.of(2022, 6, 11),
                "gladys.kulhman@example.com",
                "Female",
                null
        );

        StudentResponse student2 = new StudentResponse(
                2L,
                LocalDate.of(2022, 8, 1),
                "1002",
                "F988",
                facultyResponse,
                "Truman",
                "Harriet",
                LocalDate.of(2023, 6, 11),
                "truman.harriet@example.com",
                "Female",
                null
        );

        List<StudentResponse> studentResponses = Arrays.asList(
                student1,
                student2
        );
    }

    @Test
    public void testCreateStudent_Success() {
        StudentRequest studentRequest = new StudentRequest();
        StudentResponse studentResponse = new StudentResponse();
        ServiceResponse<StudentResponse> serviceResponse = ServiceResponse.of(studentResponse, AppStatusCode.S20001);
        when(studentService.createStudent(any(StudentRequest.class))).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(serviceResponse, new String[]{"student"})).thenReturn("Success");

        ResponseEntity<ApiResponse<StudentResponse>> responseEntity = studentController.create(studentRequest, null);

        assertEquals(201, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody().isStatus());
        assertEquals("S20001", responseEntity.getBody().getCode());
        assertEquals("Success", responseEntity.getBody().getMessage());
    }

    @Test
    void testGetStudents_Failed() {
        ServiceResponse<List<StudentResponse>> serviceResponse = new ServiceResponse<>(
                Optional.empty(), AppStatusCode.E50001, new HashMap<>()
        );
        when(studentService.getAllStudents()).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(any(), any())).thenReturn("Internal server error");

        ServiceResponse<List<StudentResponse>> response = studentService.getAllStudents();

        assertThat(response.getStatusCode()).isEqualTo(AppStatusCode.E50001);
        assertThat(response.getData()).isEmpty();
    }

    @Test
    public void testGetStudent_Success() {
        StudentResponse studentResponse = new StudentResponse();
        ServiceResponse<StudentResponse> serviceResponse = ServiceResponse.of(studentResponse, AppStatusCode.S20005);
        when(studentService.getStudent("1")).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(serviceResponse, new String[]{"student"})).thenReturn("Success");

        ResponseEntity<ApiResponse<StudentResponse>> responseEntity = studentController.getStudent("1", null);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody().isStatus());
        assertEquals("S20005", responseEntity.getBody().getCode());
        assertEquals("Success", responseEntity.getBody().getMessage());
    }

    @Test
    public void testDeleteStudent_success() {
        ServiceResponse<Boolean> serviceResponse = ServiceResponse.of(true, AppStatusCode.S20003);
        when(studentService.deleteStudent("1")).thenReturn(serviceResponse);
        when(messagingService.getResponseMessage(serviceResponse, new String[]{"student"})).thenReturn("Success");

        ResponseEntity<ApiResponse<Boolean>> responseEntity = studentController.delete("1", null);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody().isStatus());
        assertEquals("S20003", responseEntity.getBody().getCode());
        assertEquals("Success", responseEntity.getBody().getMessage());
    }
}
