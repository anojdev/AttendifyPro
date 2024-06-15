package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.dto.CourseRequestDto;
import edu.miu.attendifypro.dto.CourseResponseDto;
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
    public CourseResponseDto createCourse(CourseRequestDto courseDto) {
        Course course = courseMapper.requestDtoToEntity(courseDto);
        course = courseRepository.save(course);
        return courseMapper.entityToResponseDto(course);
    }

    public CourseResponseDto updateCourse(Long id, CourseRequestDto courseDto) {
        Course course=courseRepository.findById(id).get();
        if(course==null)
            return null;

        course = courseMapper.requestDtoToEntity(courseDto);
        course.setId(id);
        course = courseRepository.save(course);
        return courseMapper.entityToResponseDto(course);
    }

    public CourseResponseDto getCourse(Long id) {
        Course course = courseRepository.findById(id).get();
        return courseMapper.entityToResponseDto(course);
    }

    public CourseResponseDto deleteCourse(Long id) {
        Course course = courseRepository.findById(id).get();
        if(course==null)
            return null;

        CourseResponseDto courseDto = courseMapper.entityToResponseDto(course);
        courseRepository.delete(course);
        return courseDto;
    }
}
