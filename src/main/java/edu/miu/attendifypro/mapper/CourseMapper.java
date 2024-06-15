package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.Course;
import edu.miu.attendifypro.dto.CourseRequestDto;
import edu.miu.attendifypro.dto.CourseResponseDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CourseMapper {
    public CourseResponseDto entityToResponseDto(Course course){
        if(course==null)
            return null;

        return new CourseResponseDto(
                course.getId(),
                course.getCredits(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getCourseDescription(),
                course.getDepartment(),
                course.getPrerequisites()!=null?
                    course.getPrerequisites().stream()
                        .map(a->entityToResponseDto(a))
                        .collect(Collectors.toList()) :null
        );
    }

    public Course requestDtoToEntity(CourseRequestDto dto){
        if(dto==null)
            return null;

        Course entity= new Course(
                dto.getId(),
                dto.getCredits(),
                dto.getCourseCode(),
                dto.getCourseName(),
                dto.getCourseDescription(),
                dto.getDepartment()
        );
        if(dto.getPrerequisites()!=null){
            entity.setPrerequisites(
                    dto.getPrerequisites().stream()
                            .map(a->{
                                Course c= new Course();
                                c.setId(a);
                                return c;
                            })
                            .collect(Collectors.toList())
            );
        }
        return entity;
    }
}
