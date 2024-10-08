package com.irwi.transporters.application.service.auth;

import com.irwi.transporters.application.dtos.reponses.AuthResponseDto;
import com.irwi.transporters.application.dtos.reponses.UserResponseDto;
import com.irwi.transporters.application.dtos.requests.UserRequestDto;
import com.irwi.transporters.application.mappers.UserMapper;
import com.irwi.transporters.domain.entities.UserEntity;
import com.irwi.transporters.domain.enums.Roles;
import com.irwi.transporters.domain.exception.InvalidCredentialException;
import com.irwi.transporters.domain.ports.service.interfaces.IUserService;
import com.irwi.transporters.infrastructure.persistences.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> readAll() {
        List<UserEntity> userList = userRepository.findAll();

        return userList.stream()
                .map(UserMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto readById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return UserMapper.INSTANCE.toResponseDto(user);
    }

    @Override
    public AuthResponseDto register(UserRequestDto request, Roles role) {
        //Verifica si el usuario existe
        UserEntity userDB = userRepository.findByUsernameOrEmail(request.getUsername(),request.getEmail());

        //Verifica si el usuario ya existe
        if(userDB != null){
            throw new InvalidCredentialException("Username register");
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .role(role)
                .enabled(true)
                .build();


        user = this.userRepository.save(user);

        //Genera la respuesta de registro e el token JWT
        return AuthResponseDto.builder()
                .message(user.getRole() + " successfully authenticated")
                //.token(this.jwtUtil.generateToken(user))
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
