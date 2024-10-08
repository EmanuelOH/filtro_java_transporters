package com.irwi.transporters.controller.interfaces;

import com.irwi.transporters.application.dtos.reponses.AuthResponseDto;
import com.irwi.transporters.application.dtos.reponses.UserResponseDto;
import com.irwi.transporters.application.dtos.requests.UserRequestDto;
import com.irwi.transporters.controller.crud.ReadAllController;
import com.irwi.transporters.controller.crud.ReadByIdController;
import org.springframework.http.ResponseEntity;

public interface IUserController extends
        ReadAllController<UserResponseDto>,
        ReadByIdController<UserResponseDto, Long> {
    public ResponseEntity<AuthResponseDto> registerAdmin(UserRequestDto requestDto);

    public ResponseEntity<AuthResponseDto> registerRegularUser(UserRequestDto requestDto);
}
