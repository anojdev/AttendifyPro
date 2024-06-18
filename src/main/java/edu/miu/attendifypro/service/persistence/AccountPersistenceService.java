package edu.miu.attendifypro.service.persistence;

import edu.miu.attendifypro.domain.auth.Account;

public interface AccountPersistenceService {
    Account findByEmail(String email);
}
