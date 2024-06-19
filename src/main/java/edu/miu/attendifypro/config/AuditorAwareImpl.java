package edu.miu.attendifypro.config;

/**
 * @author kush
 */

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    final
    ContextUser contextUser;

    public AuditorAwareImpl(ContextUser contextUser) {
        this.contextUser = contextUser;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(contextUser.getUser().getUsername());
    }
}
