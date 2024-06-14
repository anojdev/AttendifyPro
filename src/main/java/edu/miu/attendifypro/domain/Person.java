package edu.miu.attendifypro.domain;

import edu.miu.attendifypro.domain.auth.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author kush
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String email;

    private String gender;

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    @Embedded
    AuditInfo auditInfo;

}
