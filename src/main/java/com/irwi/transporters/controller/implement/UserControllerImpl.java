package com.irwi.transporters.controller.implement;

import com.irwi.transporters.application.dtos.reponses.AuthResponseDto;
import com.irwi.transporters.application.dtos.reponses.UserResponseDto;
import com.irwi.transporters.application.dtos.requests.UserRequestDto;
import com.irwi.transporters.controller.interfaces.IUserController;
import com.irwi.transporters.domain.enums.Roles;
import com.irwi.transporters.domain.ports.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Tag(name = "User", description = "Manage user-related request.")
@CrossOrigin("*")
public class UserControllerImpl implements IUserController {

    @Autowired
    private final IUserService userService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/readAll")
    @Operation(
            summary = "List all users.",
            description = "Provide the token to validate the permissions and obtain the list of users."
    )
    @Override
    public ResponseEntity<List<UserResponseDto>> readAll() {
        return ResponseEntity.ok(this.userService.readAll());
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID.",
            description = "Retrieve a user's details by their ID, with proper authentication."
    )
    @Override
    public ResponseEntity<UserResponseDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.readById(id));
    }

    //@SecurityRequirement(name = "bearerAuth")
    @PostMapping("register/admin")
    @Operation(
            summary = "Create an admin.",
            description = "Provides the user data to create it and token to validate the permissions."
    )
    @Override
    public ResponseEntity<AuthResponseDto> registerAdmin(@Validated @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(this.userService.register(requestDto, Roles.ADMIN));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("register/carrier")
    @Operation(
            summary = "Create an regular user.",
            description = "Provides the user data to create it and token to validated the permissions."
    )
    @Override
    public ResponseEntity<AuthResponseDto> registerRegularUser(@Validated @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(this.userService.register(requestDto, Roles.TRANSPORTER));
    }
}
