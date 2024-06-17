package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.Faculty;
import edu.miu.attendifypro.domain.Student;
import edu.miu.attendifypro.dto.request.StudentRequest;
import edu.miu.attendifypro.dto.response.FacultyResponse;
import edu.miu.attendifypro.dto.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentDtoMapper {
    StudentDtoMapper dtoMapper =
            Mappers.getMapper(StudentDtoMapper.class);

    Student studentRequestToStudent(StudentRequest studentRequest);

    StudentResponse studentToStudentResponse(Student student);

    FacultyResponse facultyToFacultyResponse(Faculty faculty);


}
