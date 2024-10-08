package com.irwi.transporters.application.dtos.reponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoadDto {
    private Long id;
    private String description;
    private String states;
    private String assignedCarrier;
    private LocalDateTime estimatedDelivery;
}
