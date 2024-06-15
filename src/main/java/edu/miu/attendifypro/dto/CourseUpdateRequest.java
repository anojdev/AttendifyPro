package edu.miu.attendifypro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kush
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseUpdateRequest {
    private int credits;

    @NotBlank(message = "{should.not.be.empty}")
    @NotNull(message = "{required}")
    @Size(max = 15, message = "{validation.name.size.exceed}")
    private String courseCode;

    @NotBlank(message = "should.not.be.empty")
    @NotNull(message = "{required}")
    @Size(max = 200, message = "{validation.name.size.exceed}")
    private String courseName;

    private String courseDescription;

    @NotBlank(message = "{should.not.be.empty}")
    @NotNull(message = "{required}")
    private String department;

    private List<Long> prerequisites=new ArrayList<>();
}
