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

public class LocationType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String type;
    @Embedded
    AuditInfo auditInfo;
}
