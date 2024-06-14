package edu.miu.attendifypro.dto;

import edu.miu.attendifypro.domain.Faculty;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private LocalDate entry;
    private String studentId;
    private String applicantId;
    private Faculty facultyAdvisor;
}
