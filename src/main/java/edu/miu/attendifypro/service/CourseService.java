package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.CourseDto;

public interface CourseService {
    CourseDto createCourse(CourseDto courseDto);
    CourseDto updateCourse(Long id,CourseDto courseDto);
    CourseDto getCourse(Long id);
    CourseDto deleteCourse(Long id);
}
