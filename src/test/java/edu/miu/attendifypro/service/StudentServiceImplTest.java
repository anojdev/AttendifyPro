package edu.miu.attendifypro.service;


import edu.miu.attendifypro.domain.*;
import edu.miu.attendifypro.dto.request.StudentRequest;
import edu.miu.attendifypro.dto.response.StudentResponse;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.service.persistence.StudentCourseSelectionPersistenceService;
import edu.miu.attendifypro.service.persistence.StudentPersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class StudentServiceImplTest {
    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentPersistenceService persistenceService;

    @Mock
    private StudentCourseSelectionPersistenceService studentCourseSelectionPersistenceService;

    private Student student;
    private StudentResponse studentResponse;
    private StudentRequest studentCreateRequest;
    private StudentRequest studentUpdateRequest;
    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setId(1L);
        student.setStudentId("202");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john@doe.com");

        studentResponse = new StudentResponse();
        studentResponse.setId(1L);
        studentResponse.setStudentId("202");
        studentResponse.setFirstName("John");
        studentResponse.setLastName("Doe");
        studentResponse.setEmail("john@doe.com");

        studentCreateRequest = new StudentRequest();
        studentCreateRequest.setStudentId("202");
        studentCreateRequest.setFirstName("Jane");
        studentCreateRequest.setLastName("Doe");
        studentCreateRequest.setEmail("jane@doe.com");

        studentUpdateRequest = new StudentRequest();
        studentUpdateRequest.setStudentId("202");
        studentCreateRequest.setFirstName("Tracy");
        studentCreateRequest.setLastName("Pat");
        studentCreateRequest.setEmail("tracy@doe.com");
    }

    @Test
    public void testGetAllStudents() {
        when(persistenceService.findAll()).thenReturn(List.of(student));

        ServiceResponse<List<StudentResponse>> response = studentService.getAllStudents();
        assertEquals(AppStatusCode.S20000, response.getStatusCode());
        assertFalse(response.getData().isEmpty());
    }

    @Test
    public void testGetStudent_Success() {
        when(persistenceService.findByStudentId("1")).thenReturn(Optional.of(student));
        when(studentCourseSelectionPersistenceService.findByStudentId("1")).thenReturn(List.of());

        ServiceResponse<StudentResponse> response = studentService.getStudent("1");
        assertEquals(AppStatusCode.S20005, response.getStatusCode());
        assertNotNull(response.getData());
    }

    @Test
    public void testGetStudent_NotFound() {
        when(persistenceService.findByStudentId("1")).thenReturn(Optional.empty());

        ServiceResponse<StudentResponse> response = studentService.getStudent("1");
        assertEquals(AppStatusCode.E40004, response.getStatusCode());
    }

    @Test
    public void testGetStudentPage() {
        Page<Student> studentPage = new PageImpl<>(List.of(student));
        Pageable pageable = Pageable.unpaged();
        when(persistenceService.findAll(pageable)).thenReturn(studentPage);

        ServiceResponse<Page<StudentResponse>> response = studentService.getStudentPage(pageable);
        assertEquals(AppStatusCode.S20000, response.getStatusCode());
        assertFalse(response.getData().isEmpty());
    }

    @Test
    public void testUpdateStudent_NotFound() {
        StudentRequest studentRequest = new StudentRequest();
        when(persistenceService.findByStudentId("1")).thenReturn(Optional.empty());

        ServiceResponse<StudentResponse> response = studentService.updateStudent("1", studentRequest);
        assertEquals(AppStatusCode.E40006, response.getStatusCode());
    }
  @Test
  public void testDeleteStudent_Success() {
     Student student = new Student();
       when(persistenceService.findByStudentId("1")).thenReturn(Optional.of(student));

       ServiceResponse<Boolean> response = studentService.deleteStudent("1");
       assertEquals(AppStatusCode.S20003, response.getStatusCode());
        assertTrue(response.getData().get());
    }

    @Test
    public void testDeleteStudent_NotFound() {
        when(persistenceService.findByStudentId("1")).thenReturn(Optional.empty());

        ServiceResponse<Boolean> response = studentService.deleteStudent("1");
        assertEquals(AppStatusCode.E40004, response.getStatusCode());
    }
}
