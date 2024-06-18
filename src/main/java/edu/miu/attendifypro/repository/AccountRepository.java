package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.auth.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
}
