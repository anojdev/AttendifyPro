package edu.miu.attendifypro.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author kush
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course extends AuditInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int credits;

    @Column(nullable = false,length = 15,unique = true)
    private String courseCode;

    @Column(nullable = false,length = 200)
    private String courseName;

    private String courseDescription;

    private String department;

    @ManyToMany
    @JoinTable(
            name = "course_prerequisite",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_id"))
    private List<Course> prerequisites;


}
