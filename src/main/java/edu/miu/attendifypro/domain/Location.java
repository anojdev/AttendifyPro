package edu.miu.attendifypro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kush
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int capacity;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private LocationType locationType;

    @Embedded
    AuditInfo auditInfo;
}
