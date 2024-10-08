package com.irwi.transporters.application.mappers;

import com.irwi.transporters.application.dtos.reponses.UserResponseDto;
import com.irwi.transporters.application.dtos.requests.UserRequestDto;
import com.irwi.transporters.domain.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity (UserRequestDto requestDto);
    UserResponseDto toResponseDto (UserEntity user);

}
