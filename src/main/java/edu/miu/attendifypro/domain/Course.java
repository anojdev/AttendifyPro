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
@RequiredArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int credits;

    @NonNull
    @Column(nullable = false,length = 15)
    private String courseCode;

    @NonNull
    @Column(nullable = false,length = 200)
    private String courseName;

    @NonNull
    private String courseDescription;

    @NonNull
    private String department;

    @NonNull
    @ManyToMany
    @JoinTable(
            name = "course_prerequisite",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_id"))
    private List<Course> prerequisites;

    @Embedded
    AuditInfo auditInfo;
}
