package edu.miu.attendifypro.dto.request;

import edu.miu.attendifypro.domain.Faculty;
import edu.miu.attendifypro.domain.auth.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    @NotNull(message = "{required}")
    private LocalDate entry;

    @NotBlank(message = "should.not.be.empty")
    @NotNull(message = "{required}")
    @Size(max = 15, message = "{validation.name.size.exceed}")
    private String studentId;

    @NotNull(message = "{required}")
    @Size(max = 20, message = "{validation.name.size.exceed}")
    private String applicantId;

    private String facultyAdvisorId;

    @NotBlank(message = "should.not.be.empty")
    @NotNull(message = "{required}")
    @Size(max = 50, message = "{validation.name.size.exceed}")
    private String firstName;


    @NotBlank(message = "should.not.be.empty")
    @NotNull(message = "{required}")
    @Size(max = 50, message = "{validation.name.size.exceed}")
    private String lastName;

    private LocalDate birthDate;

    @Email
    @NotBlank(message = "should.not.be.empty")
    @NotNull(message = "{required}")
    @Size(max = 30, message = "{validation.name.size.exceed}")
    private String email;

    private String gender;

    private Account account;

}
