package com.irwi.transporters.application.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargesRequestDto {
    @NotNull(message = "the weight of the load is necessary")
    private Long weight;

    @NotNull(message = "pallet id required!")
    private Long pallet_id;
}
