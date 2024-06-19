package edu.miu.attendifypro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author kush
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceReportDto {
    private Long studentId;
    private String studentName;
    private LocalDateTime scanDateTime;
    private LocalDate day;
    private boolean isPresent;
}
