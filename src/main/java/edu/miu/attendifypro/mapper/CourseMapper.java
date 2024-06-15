package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.dto.CourseDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CourseMapper {
    public CourseDto entityToDto(Course course){
        if(course==null)
            return null;

        return new CourseDto(
                course.getId(),
                course.getCredits(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getCourseDescription(),
                course.getDepartment(),
                course.getPrerequisites()!=null?
                    course.getPrerequisites().stream()
                        .map(a->entityToDto(a))
                        .collect(Collectors.toList()) :null
        );
    }

    public Course dtoToEntity(CourseDto course){
        if(course==null)
            return null;

        Course entity= new Course(
                course.getId(),
                course.getCredits(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getCourseDescription(),
                course.getDepartment()
        );
        if(course.getPrerequisites()!=null && course.getPrerequisites().size()>0){
            entity.setPrerequisites(
                    course.getPrerequisites().stream()
                            .map(a->dtoToEntity(a))
                            .collect(Collectors.toList())
            );
        }
        return entity;
    }
}
