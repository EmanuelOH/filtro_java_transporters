package com.irwi.transporters.application.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PalletsRequestDto {
    @NotNull(message = "must add the maximum capacity")
    private Long capacity_max;

    @NotNull(message = "must add the location")
    private String location;
}
