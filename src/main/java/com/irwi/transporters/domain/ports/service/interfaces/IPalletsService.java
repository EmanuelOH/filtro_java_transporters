package com.irwi.transporters.domain.ports.service.interfaces;

import com.irwi.transporters.application.dtos.reponses.PalletsResponseDto;
import com.irwi.transporters.application.dtos.requests.PalletsRequestDto;
import com.irwi.transporters.domain.entities.Charges;
import com.irwi.transporters.domain.entities.Pallets;
import com.irwi.transporters.domain.ports.service.crud.*;

import java.util.List;
import java.util.Set;

public interface IPalletsService extends
        CreateService<PalletsRequestDto, Pallets>,
        ReadAllService<PalletsResponseDto>,
        ReadByIdService<PalletsResponseDto, Long>,
        UpdateService<PalletsRequestDto, Pallets, Long>,
        DeleteService<Long> {
    Set<Charges> getCharges(Long id);
}
