package com.irwi.transporters.domain.ports.service.interfaces;

import com.irwi.transporters.application.dtos.reponses.AuthResponseDto;
import com.irwi.transporters.application.dtos.requests.AuthRequestDto;

public interface IAuthService {
    public AuthResponseDto login (AuthRequestDto requestDto);
}
