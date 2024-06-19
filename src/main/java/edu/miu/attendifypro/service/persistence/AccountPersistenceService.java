package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.auth.Account;

import java.util.Optional;

public interface AccountPersistenceService {
    Account findByEmail(String email);
    Optional<Account> findByUsername(String username);
}
