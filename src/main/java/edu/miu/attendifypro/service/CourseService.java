package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.CourseRequestDto;
import edu.miu.attendifypro.dto.CourseResponseDto;

public interface CourseService {
    CourseResponseDto createCourse(CourseRequestDto courseDto);
    CourseResponseDto updateCourse(Long id, CourseRequestDto courseDto);
    CourseResponseDto getCourse(Long id);
    CourseResponseDto deleteCourse(Long id);
}
