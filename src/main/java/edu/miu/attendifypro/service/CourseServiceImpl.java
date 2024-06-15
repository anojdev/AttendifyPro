package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.dto.CourseDto;
import edu.miu.attendifypro.mapper.CourseMapper;
import edu.miu.attendifypro.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = courseMapper.dtoToEntity(courseDto);
        course = courseRepository.save(course);
        courseDto.setId(course.getId());
        return courseDto;
    }

    public CourseDto updateCourse(Long id,CourseDto courseDto) {
        Course course=courseRepository.findById(id).get();
        if(course==null)
            return null;

        course = courseMapper.dtoToEntity(courseDto);
        course.setId(id);
        courseRepository.save(course);
        return courseDto;
    }

    public CourseDto getCourse(Long id) {
        Course course = courseRepository.findById(id).get();
        return courseMapper.entityToDto(course);
    }

    public CourseDto deleteCourse(Long id) {
        Course course = courseRepository.findById(id).get();
        if(course==null)
            return null;

        CourseDto courseDto = courseMapper.entityToDto(course);
        courseRepository.delete(course);
        return courseDto;
    }
}
