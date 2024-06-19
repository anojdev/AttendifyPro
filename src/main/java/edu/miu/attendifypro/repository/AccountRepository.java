package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.auth.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
//    Optional<Account> findByUserId(String userName);
    Optional<Account> findByUsername(String userName);
}
