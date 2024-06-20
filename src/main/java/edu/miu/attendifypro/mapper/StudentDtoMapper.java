package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.CourseOffering;
import edu.miu.attendifypro.domain.Faculty;
import edu.miu.attendifypro.domain.Student;
import edu.miu.attendifypro.domain.StudentCourseSelection;
import edu.miu.attendifypro.dto.request.StudentRequest;
import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.response.FacultyResponse;
import edu.miu.attendifypro.dto.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentDtoMapper {
    StudentDtoMapper dtoMapper =
            Mappers.getMapper(StudentDtoMapper.class);

    Student studentRequestToStudent(StudentRequest studentRequest);

    StudentResponse studentToStudentResponse(Student student);

    FacultyResponse facultyToFacultyResponse(Faculty faculty);

    CourseResponse courseOfferingToCourseDTO(CourseOffering courseOffering);
    default List<CourseResponse> courseSelectionsToCourseDTOs(List<StudentCourseSelection> courseSelections) {
        return courseSelections.stream()
                .map(selection -> {
                    CourseResponse courseDTO = courseOfferingToCourseDTO(selection.getCourseOffering());
                    courseDTO.setCourseName( selection.getCourseOffering().getCourses().getCourseName());
                    courseDTO.setCredits(selection.getCourseOffering().getCourses().getCredits());
                    courseDTO.setCourseDescription(selection.getCourseOffering().getCourses().getCourseDescription());
                    courseDTO.setDepartment(selection.getCourseOffering().getCourses().getDepartment());
                    courseDTO.setCourseCode( selection.getCourseOffering().getCourses().getCourseCode());
                    return courseDTO;
                })
                .collect(Collectors.toList());
    }


}
