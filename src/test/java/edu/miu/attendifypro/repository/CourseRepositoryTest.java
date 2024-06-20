package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void findByCourseCodePositive() {
        Course course=new Course(null,4,"FPP","FPP","FPP","1249",new ArrayList<>());
        testEntityManager.persist(course);
        testEntityManager.flush();
        Optional<Course> courseResult=courseRepository.findByCourseCode("FPP");
        assertEquals(courseResult.isPresent(),true);
        assertEquals(courseResult.get().getCourseCode(),course.getCourseCode());
    }

    @Test
    void findByCourseCodeNegative() {
        Course course=new Course(null,4,"FPP","FPP","FPP","1249",new ArrayList<>());
        testEntityManager.persist(course);
        testEntityManager.flush();
        Optional<Course> courseResult=courseRepository.findByCourseCode("MPP");
        assertThat(courseResult.isPresent());
        assertThat(courseResult.get().getCourseCode().equals(course.getCourseCode()));
    }

}
