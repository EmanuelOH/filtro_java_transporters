package com.irwi.transporters.infrastructure.security;

import com.irwi.transporters.domain.enums.Roles;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {
    @Autowired
    private final JwtFilter jwtFilter;

    @Autowired
    private final AuthenticationProvider authenticationProvider;

    private final String[] PUBLIC_ENDPOINT = {
            "/api/auth/login",
            "/swagger-ui/**",
            "/v3/api-docs/**",
    };

    private static final String[] ADMIN_ENDPOINTS = {
            "api/users/**",
            "/api/users/register/carrier",
            "/api/users/register/admin",
            "/api/pallets/**",
            "/api/loads/**",
            "/api/audit-logs/**",
            "/api/pallets/{id}/loads"
    };

    private static final String[] TRANSPORTER_ENDPOINTS = {
            "/api/loads/{id}/status",
            "/api/loads/{id}/damage",
            "/api/carriers/loads",
            "/api/pallets/{id}/loads"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINT).permitAll()
                        .requestMatchers(TRANSPORTER_ENDPOINTS).hasAnyAuthority(Roles.TRANSPORTER.name(), Roles.ADMIN.name())
                        .requestMatchers(ADMIN_ENDPOINTS).hasAuthority(Roles.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
