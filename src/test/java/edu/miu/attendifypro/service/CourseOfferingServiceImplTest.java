package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.*;
import edu.miu.attendifypro.dto.request.CourseCreateRequest;
import edu.miu.attendifypro.dto.request.CourseOfferingCreateRequest;
import edu.miu.attendifypro.dto.request.CourseOfferingUpdateRequest;
import edu.miu.attendifypro.dto.request.CourseUpdateRequest;
import edu.miu.attendifypro.dto.response.*;
import edu.miu.attendifypro.dto.response.common.ServiceResponse;
import edu.miu.attendifypro.mapper.CourseOfferingDtoMapper;
import edu.miu.attendifypro.service.persistence.CourseOfferingPersistenceService;
import edu.miu.attendifypro.service.persistence.CoursePersistenceService;
import edu.miu.attendifypro.service.persistence.StudentPersistenceService;
import edu.miu.attendifypro.service.persistence.impl.StudentCourseSelectionPersistenceServiceImpl;
import edu.miu.attendifypro.service.persistence.impl.StudentPersistenceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseOfferingServiceImplTest {
    @Mock
    private CourseOfferingPersistenceService persistenceService;
    @InjectMocks
    private CourseOfferingServiceImpl courseOfferingService;

    @InjectMocks
    private StudentPersistenceServiceImpl studentPersistenceService;

    @InjectMocks
    private StudentCourseSelectionPersistenceServiceImpl studentCoursePersistence;

    private CourseOffering courseOffering;
    private Faculty faculty;
    private Course course;
    private CourseResponse courseResponse;
    private CourseOfferingResponse courseOfferingResponse;
    private CourseOfferingCreateRequest createRequest;
    private CourseOfferingUpdateRequest updateRequest;
    private List<CourseOffering> offeringList;

    @BeforeEach
    void setUp() {
        course=new Course();
        course.setId(1L);
        course.setCourseCode("CS101");
        course.setCourseName("Introduction to Computer Science");

        faculty = new Faculty("salute1","Dr. John Doe");

        courseOffering = new CourseOffering();
        courseOffering.setId(1L);
        courseOffering.setCapacity(30);
        courseOffering.setCourseOfferingType("Lecture");
        courseOffering.setLocation(new Location(1l,200,"Building A", new LocationType(1L, "Lecture Hall"))); // Assuming Location has this constructor
        courseOffering.setStartDate(LocalDate.of(2023, 6, 1));
        courseOffering.setEndDate(LocalDate.of(2023, 6, 5));
        courseOffering.setFaculty(faculty); // Assuming Faculty has this constructor
        courseOffering.setCourses(course); // Assuming Course has this constructor


        courseResponse=new CourseResponse();
        courseResponse.setId(1L);
        courseResponse.setCourseCode("CS101");
        courseResponse.setCourseName("Introduction to Computer Science");

        CourseOfferingResponse courseOfferingResponse = new CourseOfferingResponse(
                courseOffering.getId(),
                courseOffering.getCapacity(),
                courseOffering.getCourseOfferingType(),
                new LocationResponse(1l,200,"Building A", new LocationTypeDto(1L, "Lecture Hall")),
                courseOffering.getStartDate(),
                courseOffering.getEndDate(),
                new FacultyResponse( 1L,
                        "John",
                        "Doe",
                        LocalDate.of(1980, 1, 1),
                        "john.doe@example.com",
                        "Male",
                        faculty.getSalutation(),
                        faculty.getName()
                ),
                courseResponse
        );
        offeringList = List.of(courseOffering);

        createRequest = new CourseOfferingCreateRequest();
        createRequest.setCapacity(56);
        createRequest.setCourseOfferingType("Distance education");

        updateRequest = new CourseOfferingUpdateRequest(30, "Lecture", 1L, LocalDate.of(2024, 1, 15), LocalDate.of(2024, 5, 15), 1L, 1L);
    }
    @Test
    void getAllCourseOfferings_Success() {
        when(persistenceService.findAll()).thenReturn(offeringList);

        ServiceResponse<List<CourseOfferingResponse>> response = courseOfferingService.getAllCourseOfferings();

        assertEquals(AppStatusCode.S20000, response.getStatusCode());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().get().size());
        verify(persistenceService, times(1)).findAll();
    }

    @Test
    void getAllCourseOfferings_Failure() {
        when(persistenceService.findAll()).thenThrow(new RuntimeException());

        ServiceResponse<List<CourseOfferingResponse>> response = courseOfferingService.getAllCourseOfferings();

        assertEquals(AppStatusCode.E50002, response.getStatusCode());
        verify(persistenceService, times(1)).findAll();
    }

    @Test
    void getCourseOffering_Success() {
        when(persistenceService.findById(1L)).thenReturn(Optional.of(courseOffering));

        ServiceResponse<CourseOfferingResponse> response = courseOfferingService.getCourseOfferingById(1L);

        assertEquals(AppStatusCode.S20001, response.getStatusCode());
        assertNotNull(response.getData());
        verify(persistenceService, times(1)).findById(1L);
    }

    @Test
    void getCourseOffering_NotFound() {
        when(persistenceService.findById(1L)).thenReturn(Optional.empty());

        ServiceResponse<CourseOfferingResponse> response = courseOfferingService.getCourseOfferingById(1L);

        assertEquals(AppStatusCode.E40004, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
    }
    @Test
    void getCourseOffering_Failure() {
        when(persistenceService.findById(1L)).thenThrow(new RuntimeException());

        ServiceResponse<CourseOfferingResponse> response = courseOfferingService.getCourseOfferingById(1L);

        assertEquals(AppStatusCode.E50002, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
    }

    @Test
    void createCourseOffering_Success() {
        ServiceResponse<CourseOfferingResponse> response = courseOfferingService.createCourseOffering(createRequest);

        assertEquals(AppStatusCode.S20001, response.getStatusCode());
        assertNotNull(response.getData());
        verify(persistenceService, times(1)).save(any(CourseOffering.class));
    }

    @Test
    void createCourseOffering_Failure() {
        when(persistenceService.save(any(CourseOffering.class))).thenThrow(new RuntimeException());

        ServiceResponse<CourseOfferingResponse> response = courseOfferingService.createCourseOffering(createRequest);

        assertEquals(AppStatusCode.E50003, response.getStatusCode());
        verify(persistenceService, times(1)).save(any(CourseOffering.class));
    }

    @Test
    void updateCourseOffering_Success() {
        when(persistenceService.findById(1L)).thenReturn(Optional.of(courseOffering));
        when(persistenceService.save(any(CourseOffering.class))).thenReturn(courseOffering);

        ServiceResponse<CourseOfferingResponse> response = courseOfferingService.updateCourseOffering(1L, updateRequest);

        assertEquals(AppStatusCode.S20002, response.getStatusCode());
        assertNotNull(response.getData());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(1)).save(any(CourseOffering.class));
    }

    @Test
    void updateCourse_NotFound() {
        when(persistenceService.findById(1L)).thenReturn(Optional.empty());

        ServiceResponse<CourseOfferingResponse> response = courseOfferingService.updateCourseOffering(1L, updateRequest);

        assertEquals(AppStatusCode.E40004, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(0)).save(any(CourseOffering.class));
    }

    @Test
    void updateCourse_Failure() {
        when(persistenceService.findById(1L)).thenReturn(Optional.of(courseOffering));
        when(persistenceService.save(any(CourseOffering.class))).thenThrow(new RuntimeException());

        ServiceResponse<CourseOfferingResponse> response = courseOfferingService.updateCourseOffering(1L, updateRequest);

        assertEquals(AppStatusCode.E50002, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(1)).save(any(CourseOffering.class));
    }

    @Test
    void deleteCourse_Success() {
        when(persistenceService.findById(1L)).thenReturn(Optional.of(courseOffering));

        ServiceResponse<Boolean> response = courseOfferingService.deleteCourseOffering(1L);

        assertEquals(AppStatusCode.S20003, response.getStatusCode());
        assertTrue(response.getData().get());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(1)).delete(any(CourseOffering.class));
    }

    @Test
    void deleteCourse_NotFound() {
        when(persistenceService.findById(1L)).thenReturn(Optional.empty());

        ServiceResponse<Boolean> response = courseOfferingService.deleteCourseOffering(1L);

        assertEquals(AppStatusCode.E40004, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(0)).delete(any(CourseOffering.class));
    }

    @Test
    void deleteCourse_Failure() {
        when(persistenceService.findById(1L)).thenReturn(Optional.of(courseOffering));
        doThrow(new RuntimeException()).when(persistenceService).delete(any(CourseOffering.class));

        ServiceResponse<Boolean> response = courseOfferingService.deleteCourseOffering(1L);

        assertEquals(AppStatusCode.E50005, response.getStatusCode());
        verify(persistenceService, times(1)).findById(1L);
        verify(persistenceService, times(1)).delete(any(CourseOffering.class));
    }
}
