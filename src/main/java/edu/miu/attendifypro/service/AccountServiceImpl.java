package edu.miu.attendifypro.service;

import edu.miu.attendifypro.domain.auth.Account;
import edu.miu.attendifypro.domain.auth.Role;
import edu.miu.attendifypro.service.persistence.AccountPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements UserDetailsService {
    @Autowired
    private  AccountPersistenceService persistenceService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = persistenceService.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.withUsername(account.getEmail())
                .password(account.getPassword())
                .roles(account.getRoles().stream()
                        .map(Role::getCode)
                        .collect(Collectors.joining(", ")))
                .build();
    }
}
