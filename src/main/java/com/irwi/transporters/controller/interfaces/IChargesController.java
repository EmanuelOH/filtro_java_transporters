package com.irwi.transporters.controller.interfaces;

import com.irwi.transporters.application.dtos.reponses.ChargesResponseDto;
import com.irwi.transporters.application.dtos.requests.ChargesRequestDto;
import com.irwi.transporters.controller.crud.*;
import com.irwi.transporters.domain.entities.Charges;
import com.irwi.transporters.domain.enums.StatesCharges;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IChargesController extends
        CreateController<ChargesRequestDto, Charges>,
        ReadAllController<ChargesResponseDto>,
        ReadByIdController<ChargesResponseDto, Long>,
        UpdateController<ChargesRequestDto, Charges, Long>,
        DeleteController<Long>,
        AssignUsersController<Charges, Long, Long>{
    //public ResponseEntity<List<ChargesResponseDto>> getCharges();

    public ResponseEntity<Charges> changeStatus(StatesCharges state, Long id);

    ResponseEntity<Void> sendReportDamage(Long id, String messageContent);
}
