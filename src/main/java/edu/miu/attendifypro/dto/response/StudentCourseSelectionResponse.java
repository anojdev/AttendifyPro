package edu.miu.attendifypro.dto.response;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseSelectionResponse {
    private long id;

    private CourseOfferingResponse courseOffering;

    private StudentResponse student;
}
