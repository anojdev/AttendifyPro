package edu.miu.attendifypro.dto.response.report;

import edu.miu.attendifypro.dto.response.FacultyResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseOfferingWithRosterResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private FacultyReportResponse faculty;
    List<StudentReportResponse> students;
}
