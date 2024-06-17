package edu.miu.attendifypro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyResponse {

    private long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String email;

    private String gender;

    private String salutation;

    private String name;
}
