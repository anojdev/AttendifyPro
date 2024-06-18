package edu.miu.attendifypro.service.persistence.impl;

import edu.miu.attendifypro.domain.auth.Account;
import edu.miu.attendifypro.repository.AccountRepository;
import edu.miu.attendifypro.repository.StudentRepository;
import edu.miu.attendifypro.service.persistence.AccountPersistenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AccountPersistenceServiceImpl implements AccountPersistenceService {
    private final AccountRepository accountRepository;


    public AccountPersistenceServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}
