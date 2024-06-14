package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.dto.CourseDto;
import edu.miu.attendifypro.mapper.CourseMapper;
import edu.miu.attendifypro.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseRepository courseRepository;
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = courseMapper.dtoToEntity(courseDto);
        courseRepository.save(course);
        return courseMapper.entityToDto(courseRepository.findById(course.getId()).get());
    }

    public CourseDto updateCourse(Long id,CourseDto courseDto) {
        Course course=courseRepository.findById(id).get();
        if(course==null)
            return null;

        course = courseMapper.dtoToEntity(courseDto);
        course.setId(id);
        courseRepository.save(course);
        return courseMapper.entityToDto(courseRepository.findById(course.getId()).get());
    }

    public CourseDto getAccount(Long id) {
        Course course = courseRepository.findById(id).get();
        return courseMapper.entityToDto(course);
    }
}
