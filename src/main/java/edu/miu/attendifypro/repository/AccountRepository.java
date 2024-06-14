package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.auth.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kush
 */
public interface AccountRepository extends JpaRepository<Account,Long> {
}
