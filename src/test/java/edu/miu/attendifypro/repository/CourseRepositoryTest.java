package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    private Course course;

    @BeforeEach
    public void setUp() {
        course = new Course();
        course.setCourseCode("CS101");
        course.setCourseName("Introduction to Computer Science");
        course.setCredits(4);
        course.setDepartment("Science");
        courseRepository.save(course);
    }

    @Test
    public void whenFindByCourseCode_thenReturnCourse() {
        Optional<Course> foundCourse = courseRepository.findByCourseCode("CS101");

        assertThat(foundCourse).isPresent();
        assertThat(foundCourse.get().getCourseCode()).isEqualTo("CS101");
        assertThat(foundCourse.get().getCourseName()).isEqualTo("Introduction to Computer Science");
    }

    @Test
    public void whenFindByInvalidCourseCode_thenReturnEmpty() {
        Optional<Course> foundCourse = courseRepository.findByCourseCode("INVALID");

        assertThat(foundCourse).isNotPresent();
    }
}
