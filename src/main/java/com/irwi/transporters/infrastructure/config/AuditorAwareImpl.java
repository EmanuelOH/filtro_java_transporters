package com.irwi.transporters.infrastructure.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

    public class AuditorAwareImpl implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String auditor = authentication.getName();
                System.out.println("Auditor: " + auditor);
                return Optional.of(auditor);
            }
            return Optional.empty();
        }
    }

