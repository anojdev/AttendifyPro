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

import java.util.Optional;
import java.util.stream.Collectors;

@Service("AccountServiceImpl")
public class AccountServiceImpl implements  UserDetailsService {
    @Autowired
    private  AccountPersistenceService persistenceService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        Account account = persistenceService.findByEmail(email);
        Optional<Account> account = persistenceService.findByUsername(userName);
        if (account.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        Role accountRoles =  account.get().getRoles().iterator().hasNext() ? account.get().getRoles().iterator().next() : null;
//                user.get().getSystemRoles().iterator().hasNext() ? user.get().getSystemRoles().iterator().next() : null;

        return  new SecurityUser(account.get().getEmail(), account.get().getUsername(), account.get().getPassword(), account.get().isEnabled(),
             account.get().getRoles());
//        return new SecurityUser(account.getId(),
//                account.getUsername(),
//                account.getPassword(),
////                account.isEnabled() == true,
//                accountRoles);
//        return User.withUsername(account.getEmail())
//                .password(account.getPassword())
//                .roles(account.getRoles().stream()
//                        .map(Role::getCode)
//                        .collect(Collectors.joining(", ")))
//                .build();
    }
}
