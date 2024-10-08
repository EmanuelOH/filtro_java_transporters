package com.irwi.transporters.application.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDto {
    @NotBlank(message = "Identifier required")
    private String identifier;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
