package edu.miu.attendifypro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author kush
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentAttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime scanDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Location location;

}
