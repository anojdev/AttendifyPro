package edu.miu.attendifypro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private int credits;
    private String courseCode;
    private String courseName;
    private String courseDescription;
    private String department;
    private List<CourseResponse> prerequisites;
}
