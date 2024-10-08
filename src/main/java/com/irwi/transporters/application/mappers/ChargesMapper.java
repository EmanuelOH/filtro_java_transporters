package com.irwi.transporters.application.mappers;

import com.irwi.transporters.application.dtos.reponses.ChargesResponseDto;
import com.irwi.transporters.application.dtos.requests.ChargesRequestDto;
import com.irwi.transporters.domain.entities.Charges;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChargesMapper {
    ChargesMapper INSTANCE = Mappers.getMapper(ChargesMapper.class);

    Charges toEntity (ChargesRequestDto requestDto);

    @Mapping(target = "user", source = "user")
    ChargesResponseDto toResponseDto (Charges charge);
}
