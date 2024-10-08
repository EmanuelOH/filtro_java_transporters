package com.irwi.transporters.application.dtos.reponses;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PalletsResponseDto {
    private Long id;
    private Long capacity_max;
    private Long current_capacity;
    private String location;
    private String state;
    private Set<ChargesResponseDto> charges;
}
