package edu.miu.attendifypro.dto.request;

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
public class CourseOfferingCreateRequest {
    @Size(min=1, message = "{validation.name.size.exceed}")
    private int capacity;

    @NotBlank(message = "{should.not.be.empty}")
    @NotNull(message = "{required}")
    @Size(max = 500, message = "{validation.name.size.exceed}")
    private String courseOfferingType;

    private long locationId;

    @NotNull(message = "{required}")
    private LocalDate startDate;

    @NotNull(message = "{required}")
    private LocalDate endDate;

    private long facultyId;

    private long courseId;
}
