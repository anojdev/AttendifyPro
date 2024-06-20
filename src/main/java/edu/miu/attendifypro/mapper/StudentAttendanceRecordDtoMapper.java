package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.Location;
import edu.miu.attendifypro.domain.Student;
import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import edu.miu.attendifypro.dto.response.LocationResponse;
import edu.miu.attendifypro.dto.response.StudentAttendanceRecordResponse;
import edu.miu.attendifypro.dto.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentAttendanceRecordDtoMapper {
    StudentAttendanceRecordDtoMapper dtoMapper =
            Mappers.getMapper(StudentAttendanceRecordDtoMapper.class);

    StudentAttendanceRecordResponse studentAttendanceRecordResponse(StudentAttendanceRecord  studentAttendanceRecord);
    List<StudentAttendanceRecordResponse> studentAttendanceRecordResponses(List<StudentAttendanceRecord> studentAttendanceRecord);
//    StudentResponse studentToStudentResponse(Student student);

    LocationResponse locationToLocationDto(Location location);
}