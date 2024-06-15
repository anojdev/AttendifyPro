package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.StudentAttendanceRecord;
import edu.miu.attendifypro.dto.StudentAttendanceRecordDto;
import org.springframework.stereotype.Component;

@Component
public class StudentAttendanceRecordMapper {
    public StudentAttendanceRecordDto toDTO(StudentAttendanceRecord studentAttendanceRecord){
        return new StudentAttendanceRecordDto(
                studentAttendanceRecord.getId(),
                studentAttendanceRecord.getScanDateTime(),
                studentAttendanceRecord.getStudent(),
                studentAttendanceRecord.getLocation()
        );
    }

    public StudentAttendanceRecord toEntity(StudentAttendanceRecordDto studentAttendanceRecord){
        return new StudentAttendanceRecord(
                studentAttendanceRecord.getId(),
                studentAttendanceRecord.getScanDateTime(),
                studentAttendanceRecord.getStudent(),
                studentAttendanceRecord.getLocation()
        );
    }
}
