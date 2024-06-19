package edu.miu.attendifypro.service;

import edu.miu.attendifypro.config.SecurityUser;
import edu.miu.attendifypro.domain.auth.Account;
import edu.miu.attendifypro.domain.auth.Role;
import edu.miu.attendifypro.service.persistence.AccountPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("AccountServiceImpl")
public class AccountServiceImpl implements  UserDetailsService {
    @Autowired
    private  AccountPersistenceService persistenceService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Account> accountOpt = persistenceService.findByUsername(userName);
        if (accountOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        Account account=accountOpt.get();
        List<String> roles=account.getRoles().stream()
                .map(Role::getCode)
                .toList();
        return new SecurityUser(account.getId(), account.getUsername(),
                account.getPassword(), account.isEnabled(), roles);

    }
}
