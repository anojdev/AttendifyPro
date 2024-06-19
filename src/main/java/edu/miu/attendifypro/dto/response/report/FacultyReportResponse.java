package edu.miu.attendifypro.dto.response.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyReportResponse {
    private String firstName;

    private String lastName;

    private String email;
}
