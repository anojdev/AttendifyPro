package edu.miu.attendifypro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAttendanceRecordResponse {
    private Long id;
    private LocalDateTime scanDateTime;
    private StudentResponse student;
//    private LocationResponse location;
}
