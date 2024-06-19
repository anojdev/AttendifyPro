package edu.miu.attendifypro.dto.response.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseOfferingReport1Response{
    private LocalDate startDate;
    private LocalDate endDate;
    private CourseReportResponse courses;
}
