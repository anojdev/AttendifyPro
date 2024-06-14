package edu.miu.attendifypro.service;

import edu.miu.attendifypro.dto.CourseDto;

public interface CourseService {
    CourseDto createCourse(CourseDto courseDto);
    CourseDto getAccount(Long id);
}
