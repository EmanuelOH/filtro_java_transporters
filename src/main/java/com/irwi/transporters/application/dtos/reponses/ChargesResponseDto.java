package com.irwi.transporters.application.dtos.reponses;

import com.irwi.transporters.domain.entities.UserEntity;
import com.irwi.transporters.domain.enums.StatesCharges;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargesResponseDto {
    private Long id;
    private Long weight;
    private String states;
    private UserResponseDto user;
}
