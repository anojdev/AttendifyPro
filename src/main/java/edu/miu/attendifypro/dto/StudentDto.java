package edu.miu.attendifypro.dto;

import edu.miu.attendifypro.domain.AuditInfo;
import edu.miu.attendifypro.domain.Faculty;
import edu.miu.attendifypro.domain.auth.Account;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String gender;
    private Account account;
//    AuditInfo auditInfo;
}
