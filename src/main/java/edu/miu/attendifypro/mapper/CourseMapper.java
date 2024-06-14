package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.dto.CourseDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CourseMapper {
    public CourseDto entityToDto(Course course){
        return new CourseDto(
                course.getId(),
                course.getCredits(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getCourseDescription(),
                course.getDepartment(),
                course.getPrerequisites().stream()
                        .map(a->entityToDto(a))
                        .collect(Collectors.toList())
        );
    }

    public Course dtoToEntity(CourseDto course){
        return new Course(
                course.getId(),
                course.getCredits(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getCourseDescription(),
                course.getDepartment(),
                course.getPrerequisites().stream()
                        .map(a->dtoToEntity(a))
                        .collect(Collectors.toList())
        );
    }
}
