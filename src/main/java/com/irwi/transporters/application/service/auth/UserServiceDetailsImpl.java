package com.irwi.transporters.application.service.auth;

import com.irwi.transporters.domain.entities.UserEntity;
import com.irwi.transporters.infrastructure.persistences.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDetailsImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsernameOrEmail(identifier, identifier);

        if(user == null){
            throw  new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
