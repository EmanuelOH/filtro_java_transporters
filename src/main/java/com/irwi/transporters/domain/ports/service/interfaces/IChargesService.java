package com.irwi.transporters.domain.ports.service.interfaces;

import com.irwi.transporters.application.dtos.reponses.ChargesResponseDto;
import com.irwi.transporters.application.dtos.requests.ChargesRequestDto;
import com.irwi.transporters.domain.entities.Charges;
import com.irwi.transporters.domain.enums.StatesCharges;
import com.irwi.transporters.domain.ports.service.crud.*;

import java.util.List;

public interface IChargesService extends
        CreateService<ChargesRequestDto, Charges>,
        ReadAllService<ChargesResponseDto>,
        ReadByIdService<ChargesResponseDto, Long>,
        UpdateService<ChargesRequestDto, Charges, Long>,
        DeleteService<Long>,
        AssignUsersService<Charges, Long, Long>{
    List<ChargesResponseDto> getCharges();
    Charges changeStatus(StatesCharges state, Long id);

    void sendReportDamage(Long id, String messageContent);
}
