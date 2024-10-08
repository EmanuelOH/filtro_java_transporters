package com.irwi.transporters.controller.implement;

import com.irwi.transporters.application.dtos.reponses.ChargesResponseDto;
import com.irwi.transporters.domain.ports.service.interfaces.IChargesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/carriers")
@AllArgsConstructor
@Tag(name = "Carriers", description = "Manage carriers-related request.")
@CrossOrigin("*")
public class CarriersControllerImpl {
    @Autowired
    private IChargesService chargesService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("loads")
    @Operation(
            summary = "List all assign loads.",
            description = "Get the loads assigned to a carrier. Only for carriers."
    )
    public ResponseEntity<List<ChargesResponseDto>> getCharges() {
        return ResponseEntity.ok(chargesService.getCharges());
    }
}
