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

public class LocationType extends AuditInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;

}
