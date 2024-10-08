package com.irwi.transporters.domain.ports.service.interfaces;

import com.irwi.transporters.application.dtos.reponses.AuthResponseDto;
import com.irwi.transporters.application.dtos.reponses.UserResponseDto;
import com.irwi.transporters.application.dtos.requests.UserRequestDto;
import com.irwi.transporters.domain.entities.UserEntity;
import com.irwi.transporters.domain.enums.Roles;
import com.irwi.transporters.domain.ports.service.crud.DeleteService;
import com.irwi.transporters.domain.ports.service.crud.ReadAllService;
import com.irwi.transporters.domain.ports.service.crud.ReadByIdService;
import com.irwi.transporters.domain.ports.service.crud.UpdateService;

public interface IUserService extends
        ReadAllService<UserResponseDto>,
        ReadByIdService<UserResponseDto, Long> {
    public AuthResponseDto register(UserRequestDto request, Roles role);
}
