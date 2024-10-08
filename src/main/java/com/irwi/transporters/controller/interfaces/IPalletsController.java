package com.irwi.transporters.controller.interfaces;

import com.irwi.transporters.application.dtos.reponses.PalletsResponseDto;
import com.irwi.transporters.application.dtos.requests.PalletsRequestDto;
import com.irwi.transporters.controller.crud.*;
import com.irwi.transporters.domain.entities.Charges;
import com.irwi.transporters.domain.entities.Pallets;
import com.irwi.transporters.domain.ports.service.crud.UpdateService;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IPalletsController extends
        CreateController<PalletsRequestDto, Pallets>,
        ReadAllController<PalletsResponseDto>,
        ReadByIdController<PalletsResponseDto, Long>,
        UpdateController<PalletsRequestDto, Pallets, Long>,
        DeleteController<Long> {
    ResponseEntity<Set<Charges>> getCharges(Long id);
}
