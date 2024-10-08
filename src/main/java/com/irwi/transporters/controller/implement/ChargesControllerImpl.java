package com.irwi.transporters.controller.implement;

import com.irwi.transporters.application.dtos.reponses.ChargesResponseDto;
import com.irwi.transporters.application.dtos.requests.ChargesRequestDto;
import com.irwi.transporters.controller.interfaces.IChargesController;
import com.irwi.transporters.domain.entities.Charges;
import com.irwi.transporters.domain.enums.StatesCharges;
import com.irwi.transporters.domain.ports.service.interfaces.IChargesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/loads")
@AllArgsConstructor
@Tag(name = "Loads", description = "Manage loads-related request.")
@CrossOrigin("*")
public class ChargesControllerImpl implements IChargesController {

    @Autowired
    private IChargesService chargesService;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("{idCharge}/assign-load")
    @Operation(
            summary = "Modify a specific loads.",
            description = "Modify an existing load (weight, dimensions, status, etc.). For administrators only."
    )
    @Override
    public ResponseEntity<Charges> assignUser(@PathVariable Long idCharge, @RequestBody Long idUser) {
        return ResponseEntity.ok(chargesService.assignUser(idCharge, idUser));
    }


    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @Operation(
            summary = "Assign a loads.",
            description = "Assigning a load for a carrier can only be done by the admin."
    )
    @Override
    public ResponseEntity<Charges> create(@Validated @RequestBody ChargesRequestDto chargesRequestDto) {
        return ResponseEntity.ok(chargesService.create(chargesRequestDto));
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete a specific loads.",
            description = "Delete a specific loads. For administrators only."
    )
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            chargesService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityActionVetoException error) {
            return ResponseEntity.notFound().build();
        }
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    @Operation(
            summary = "List all loads.",
            description = "Obtain all loads. For administrators only."
    )
    @Override
    public ResponseEntity<List<ChargesResponseDto>> readAll() {
        return ResponseEntity.ok(chargesService.readAll());
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("{id}")
    @Operation(
            summary = "Show a specific loads.",
            description = "Obtain a specific loads. For administrators only."
    )
    @Override
    public ResponseEntity<ChargesResponseDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(chargesService.readById(id));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("{id}")
    @Operation(
            summary = "Modify a specific loads.",
            description = "Modify an existing load (weight, dimensions, status, etc.). For administrators only."
    )
    @Override
    public ResponseEntity<Charges> update(@Validated @RequestBody ChargesRequestDto chargesRequestDto,
                                          @PathVariable Long id) {
        return ResponseEntity.ok(chargesService.update(chargesRequestDto, id));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("{id}/status")
    @Operation(
            summary = "Update the status of a loads.",
            description = "Update the status of a load (PENDING, IN_TRANSIT, DELIVERED). Carrier or administrator."
    )
    @Override
    public ResponseEntity<Charges> changeStatus(@RequestBody StatesCharges state, @PathVariable Long id) {
        return ResponseEntity.ok(chargesService.changeStatus(state, id));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("{id}/damage")
    @Operation(
            summary = "Report damage",
            description = "Report cargo damage. Carrier or manager."
    )
    @Override
    public ResponseEntity<Void> sendReportDamage(@PathVariable Long id, @RequestBody String messageContent) {
        try {
            chargesService.sendReportDamage(id, messageContent);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
