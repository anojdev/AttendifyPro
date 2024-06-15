package edu.miu.attendifypro.dto;

import edu.miu.attendifypro.domain.Location;
import edu.miu.attendifypro.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAttendanceRecordDto {
    private Long id;
    private LocalDateTime scanDateTime;
    private Student student;
    private Location location;
}
