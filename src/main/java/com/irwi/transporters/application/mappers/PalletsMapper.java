package com.irwi.transporters.application.mappers;

import com.irwi.transporters.application.dtos.reponses.PalletsResponseDto;
import com.irwi.transporters.application.dtos.requests.PalletsRequestDto;
import com.irwi.transporters.domain.entities.Pallets;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PalletsMapper {
    PalletsMapper INSTANCE = Mappers.getMapper(PalletsMapper.class);

    Pallets toEntity (PalletsRequestDto requestDto);

    PalletsResponseDto toResponseDto (Pallets pallets);
}
