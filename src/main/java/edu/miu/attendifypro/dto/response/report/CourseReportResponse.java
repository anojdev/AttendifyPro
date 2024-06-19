package edu.miu.attendifypro.dto.response.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseReportResponse {
    private int credits;
    private String courseCode;
    private String courseName;
}
