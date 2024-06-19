package edu.miu.attendifypro.dto.response.report;

import edu.miu.attendifypro.dto.response.FacultyResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentReportResponse {
    private String studentId;
    private String firstName;
    private String lastName;
}
