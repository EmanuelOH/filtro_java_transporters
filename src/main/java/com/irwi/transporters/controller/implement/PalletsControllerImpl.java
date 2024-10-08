package com.irwi.transporters.controller.implement;

import com.irwi.transporters.application.dtos.reponses.PalletsResponseDto;
import com.irwi.transporters.application.dtos.requests.PalletsRequestDto;
import com.irwi.transporters.controller.interfaces.IPalletsController;
import com.irwi.transporters.domain.entities.Charges;
import com.irwi.transporters.domain.entities.Pallets;
import com.irwi.transporters.domain.ports.service.interfaces.IPalletsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/pallets")
@AllArgsConstructor
@Tag(name = "Pallets", description = "Manage pallets-related request.")
@CrossOrigin("*")
public class PalletsControllerImpl implements IPalletsController {

    @Autowired
    private IPalletsService palletsService;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @Operation(
            summary = "Create a pallet.",
            description = "Create a new pallet. For administrators only."
    )
    @Override
    public ResponseEntity<Pallets> create(@Validated @RequestBody PalletsRequestDto palletsRequestDto) {
        return ResponseEntity.ok(palletsService.create(palletsRequestDto));
    }


    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete a specific pallet.",
            description = "Delete a specific pallet. For administrators only."
    )
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            palletsService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityActionVetoException error) {
            return ResponseEntity.notFound().build();
        }
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    @Operation(
            summary = "List all pallet.",
            description = "Obtain all pallets. For administrators only."
    )
    @Override
    public ResponseEntity<List<PalletsResponseDto>> readAll() {
        return ResponseEntity.ok(palletsService.readAll());
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("{id}")
    @Operation(
            summary = "Show a specific pallet.",
            description = "Obtain a specific pallet. For administrators only."
    )
    @Override
    public ResponseEntity<PalletsResponseDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(palletsService.readById(id));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("{id}")
    @Operation(
            summary = "Modify a specific pallet.",
            description = "Modify an existing pallet (capacity, location, condition, etc.). For administrators only."
    )
    @Override
    public ResponseEntity<Pallets> update(@Validated @RequestBody PalletsRequestDto palletsRequestDto,
                                          @PathVariable  Long id) {
        return ResponseEntity.ok(palletsService.update(palletsRequestDto, id));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("{id}/loads")
    @Operation(
            summary = "Obtain all loads in to a pallet.",
            description = "Obtain the loads assigned to a specific pallet.  Carrier or administrator."
    )
    @Override
    public ResponseEntity<Set<Charges>> getCharges(@PathVariable Long id) {
        return ResponseEntity.ok(palletsService.getCharges(id));
    }
}
