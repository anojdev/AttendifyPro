package edu.miu.attendifypro.domain.auth;

import edu.miu.attendifypro.domain.AuditInfo;
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
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true,length = 20)
    private String code;

    private String description;

    @Embedded
    AuditInfo auditInfo;
}
