package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.AppStatusCode;
import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.dto.CourseCreateRequest;
import edu.miu.attendifypro.dto.CourseDto;
import edu.miu.attendifypro.dto.CourseUpdateRequest;
import edu.miu.attendifypro.dto.ServiceResponse;
import edu.miu.attendifypro.service.persistence.CoursePersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CoursePersistenceService persistenceService;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;
    private CourseDto courseDto;
    private CourseCreateRequest courseCreateRequest;
    private CourseUpdateRequest courseUpdateRequest;
    private List<Course> courseList;
    private Page<Course> coursePage;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);
        course.setCourseCode("CS101");
        course.setCourseName("Introduction to Computer Science");

        courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setCourseCode("CS101");
        courseDto.setCourseName("Introduction to Computer Science");

        courseCreateRequest = new CourseCreateRequest();
        courseCreateRequest.setCourseCode("CS101");
        courseCreateRequest.setCourseName("Introduction to Computer Science");

        courseUpdateRequest = new CourseUpdateRequest();
        courseUpdateRequest.setCourseCode("CS101");
        courseUpdateRequest.setCourseName("Introduction to Computer Science");

        courseList = List.of(course);
        coursePage = new PageImpl<>(courseList);
    }

    @Test
    void getAllCourses_Success() {
        when(persistenceService.findAll()).thenReturn(courseList);

        ServiceResponse<List<CourseDto>> response = courseService.getAllCourses();

        assertEquals(AppStatusCode.S20000, response.getStatusCode());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().get().size());
        verify(persistenceService, times(1)).findAll();
    }

    @Test
    void getAllCourses_Failure() {
        when(persistenceService.findAll()).thenThrow(new RuntimeException());

        ServiceResponse<List<CourseDto>> response = courseService.getAllCourses();

        assertEquals(AppStatusCode.E50002, response.getStatusCode());
        verify(persistenceService, times(1)).findAll();
    }

    @Test
    void getAccount_Success() {
        when(persistenceService.findById(1L)).thenReturn(Optional.of(course));

        ServiceResponse<CourseDto> response = courseService.getAccount(1L);

        assertEquals(AppStatusCode.S20005, response.getStatusCode());
        assertNotNull(response.getData());
        verify(persistenceService, times(1)).findById(1L);
    }

    @Test
    void getAccount_NotFound() {
        when(persistenceService.findById(1L)).thenReturn(Optional.empty());

        ServiceResponse<CourseDto> response = courseService.getAccount(1L);

        assertEquals(AppStatusCode.E40004, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
    }

    @Test
    void getAccount_Failure() {
        when(persistenceService.findById(1L)).thenThrow(new RuntimeException());

        ServiceResponse<CourseDto> response = courseService.getAccount(1L);

        assertEquals(AppStatusCode.E50001, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
    }

    @Test
    void getCoursePage_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        when(persistenceService.findAll(pageable)).thenReturn(coursePage);

        ServiceResponse<Page<CourseDto>> response = courseService.getCoursePage(pageable);

        assertEquals(AppStatusCode.S20000, response.getStatusCode());
        assertNotNull(response.getData());
        verify(persistenceService, times(1)).findAll(pageable);
    }

    @Test
    void getCoursePage_Failure() {
        Pageable pageable = PageRequest.of(0, 10);
        when(persistenceService.findAll(pageable)).thenThrow(new RuntimeException());

        ServiceResponse<Page<CourseDto>> response = courseService.getCoursePage(pageable);

        assertEquals(AppStatusCode.E50002, response.getStatusCode());
        verify(persistenceService, times(1)).findAll(pageable);
    }

    @Test
    void createCourse_Success() {
        when(persistenceService.findByCourseCode(anyString())).thenReturn(Optional.empty());
        when(persistenceService.save(any(Course.class))).thenReturn(course);

        ServiceResponse<CourseDto> response = courseService.createCourse(courseCreateRequest);

        assertEquals(AppStatusCode.S20001, response.getStatusCode());
        assertNotNull(response.getData());
        verify(persistenceService, times(1)).findByCourseCode(anyString());
        verify(persistenceService, times(1)).save(any(Course.class));
    }

    @Test
    void createCourse_CourseExists() {
        when(persistenceService.findByCourseCode(anyString())).thenReturn(Optional.of(course));

        ServiceResponse<CourseDto> response = courseService.createCourse(courseCreateRequest);

        assertEquals(AppStatusCode.E40006, response.getStatusCode());
        verify(persistenceService, times(1)).findByCourseCode(anyString());
        verify(persistenceService, times(0)).save(any(Course.class));
    }

    @Test
    void createCourse_DataIntegrityViolation() {
        when(persistenceService.findByCourseCode(anyString())).thenReturn(Optional.empty());
        when(persistenceService.save(any(Course.class))).thenThrow(new DataIntegrityViolationException(""));

        ServiceResponse<CourseDto> response = courseService.createCourse(courseCreateRequest);

        assertEquals(AppStatusCode.E40002, response.getStatusCode());
        verify(persistenceService, times(1)).findByCourseCode(anyString());
        verify(persistenceService, times(1)).save(any(Course.class));
    }

    @Test
    void createCourse_Failure() {
        when(persistenceService.findByCourseCode(anyString())).thenReturn(Optional.empty());
        when(persistenceService.save(any(Course.class))).thenThrow(new RuntimeException());

        ServiceResponse<CourseDto> response = courseService.createCourse(courseCreateRequest);

        assertEquals(AppStatusCode.E50003, response.getStatusCode());
        verify(persistenceService, times(1)).findByCourseCode(anyString());
        verify(persistenceService, times(1)).save(any(Course.class));
    }

    @Test
    void updateCourse_Success() {
        when(persistenceService.findById(1L)).thenReturn(Optional.of(course));
        when(persistenceService.save(any(Course.class))).thenReturn(course);

        ServiceResponse<CourseDto> response = courseService.updateCourse(1L, courseUpdateRequest);

        assertEquals(AppStatusCode.S20002, response.getStatusCode());
        assertNotNull(response.getData());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(1)).save(any(Course.class));
    }

    @Test
    void updateCourse_NotFound() {
        when(persistenceService.findById(1L)).thenReturn(Optional.empty());

        ServiceResponse<CourseDto> response = courseService.updateCourse(1L, courseUpdateRequest);

        assertEquals(AppStatusCode.E40004, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(0)).save(any(Course.class));
    }

    @Test
    void updateCourse_Failure() {
        when(persistenceService.findById(1L)).thenReturn(Optional.of(course));
        when(persistenceService.save(any(Course.class))).thenThrow(new RuntimeException());

        ServiceResponse<CourseDto> response = courseService.updateCourse(1L, courseUpdateRequest);

        assertEquals(AppStatusCode.E50002, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(1)).save(any(Course.class));
    }

    @Test
    void deleteCourse_Success() {
        when(persistenceService.findById(1L)).thenReturn(Optional.of(course));

        ServiceResponse<Boolean> response = courseService.deleteCourse(1L);

        assertEquals(AppStatusCode.S20003, response.getStatusCode());
        assertTrue(response.getData().get());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(1)).delete(any(Course.class));
    }

    @Test
    void deleteCourse_NotFound() {
        when(persistenceService.findById(1L)).thenReturn(Optional.empty());

        ServiceResponse<Boolean> response = courseService.deleteCourse(1L);

        assertEquals(AppStatusCode.E40004, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(0)).delete(any(Course.class));
    }

    @Test
    void deleteCourse_Failure() {
        when(persistenceService.findById(1L)).thenReturn(Optional.of(course));
        doThrow(new RuntimeException()).when(persistenceService).delete(any(Course.class));

        ServiceResponse<Boolean> response = courseService.deleteCourse(1L);

        assertEquals(AppStatusCode.E50005, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(1)).delete(any(Course.class));
    }
}
