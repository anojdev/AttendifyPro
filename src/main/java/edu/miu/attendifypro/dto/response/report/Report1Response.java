package edu.miu.attendifypro.dto.response.report;

import edu.miu.attendifypro.dto.response.CourseOfferingResponse;
import edu.miu.attendifypro.dto.response.CourseResponse;
import edu.miu.attendifypro.dto.response.FacultyResponse;
import edu.miu.attendifypro.dto.response.StudentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report1Response {
    private CourseOfferingReport1Response courseOffering;
    private String grade;
}

