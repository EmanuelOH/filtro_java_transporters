package com.irwi.transporters.application.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    @NotBlank(message = "username required!")
    private String username;

    @NotBlank(message = "email required!")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "password required!")
    private String password;
}
