package com.irwi.transporters.controller.interfaces;

import com.irwi.transporters.application.dtos.reponses.AuthResponseDto;
import com.irwi.transporters.application.dtos.requests.AuthRequestDto;
import org.springframework.http.ResponseEntity;

public interface IAuthController {
    public ResponseEntity<AuthResponseDto> login (AuthRequestDto requestDto);
}
