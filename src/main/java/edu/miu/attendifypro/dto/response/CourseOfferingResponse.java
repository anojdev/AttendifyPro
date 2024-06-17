package edu.miu.attendifypro.dto.response;

import edu.miu.attendifypro.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseOfferingResponse {
    private long id;

    private int capacity;

    private String courseOfferingType;

    private Location location;

    private LocalDate startDate;

    private LocalDate endDate;

    private FacultyResponse faculty;

    private CourseResponse courses;
}
