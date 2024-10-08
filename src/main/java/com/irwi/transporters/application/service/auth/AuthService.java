package com.irwi.transporters.application.service.auth;

import com.irwi.transporters.application.dtos.reponses.AuthResponseDto;
import com.irwi.transporters.application.dtos.requests.AuthRequestDto;
import com.irwi.transporters.domain.entities.UserEntity;
import com.irwi.transporters.domain.exception.InvalidCredentialException;
import com.irwi.transporters.domain.ports.service.interfaces.IAuthService;
import com.irwi.transporters.infrastructure.helpers.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public AuthResponseDto login(AuthRequestDto request) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(request.getIdentifier());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw  new InvalidCredentialException("Invalid credential");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getIdentifier()  , request.getPassword()));

        return AuthResponseDto.builder()
                .message(user.getRole() + " Successfully authentication")
                .token(this.jwtUtil.generateToken(user))
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
