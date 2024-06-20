package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CoursePersistenceServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CoursePersistenceServiceImpl coursePersistenceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllSuccess() {
        List<Course> courses = Collections.singletonList(new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = coursePersistenceService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindAllPageableSuccess() {
        List<Course> courses = Collections.singletonList(new Course());
        Page<Course> coursePage = new PageImpl<>(courses);
        Pageable pageable = PageRequest.of(0, 10);
        when(courseRepository.findAll(pageable)).thenReturn(coursePage);

        Page<Course> result = coursePersistenceService.findAll(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testFindByIdSuccess() {
        Course course = new Course();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Optional<Course> result = coursePersistenceService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(course, result.get());
    }

    @Test
    public void testFindByIdFailure() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Course> result = coursePersistenceService.findById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindByCourseCodeSuccess() {
        Course course = new Course();
        when(courseRepository.findByCourseCode("CS101")).thenReturn(Optional.of(course));

        Optional<Course> result = coursePersistenceService.findByCourseCode("CS101");
        assertTrue(result.isPresent());
        assertEquals(course, result.get());
    }

    @Test
    public void testFindByCourseCodeFailure() {
        when(courseRepository.findByCourseCode("CS101")).thenReturn(Optional.empty());

        Optional<Course> result = coursePersistenceService.findByCourseCode("CS101");
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindAllByIdSuccess() {
        HashSet<Long> ids = new HashSet<>();
        ids.add(1L);
        List<Course> courses = Collections.singletonList(new Course());
        when(courseRepository.findAllById(ids)).thenReturn(courses);

        List<Course> result = coursePersistenceService.findAllById(ids);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testSaveSuccess() {
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = coursePersistenceService.save(course);
        assertNotNull(result);
        assertEquals(course, result);
    }

    @Test
    public void testDeleteSuccess() {
        Course course = new Course();
        coursePersistenceService.delete(course);
        assertTrue(true);
    }

    @Test
    public void testSaveFailure() {
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenThrow(new RuntimeException("Save failed"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            coursePersistenceService.save(course);
        });

        assertEquals("Save failed", exception.getMessage());
    }
}
