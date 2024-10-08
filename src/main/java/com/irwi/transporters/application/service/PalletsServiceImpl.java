package com.irwi.transporters.application.service;

import com.irwi.transporters.application.dtos.reponses.PalletsResponseDto;
import com.irwi.transporters.application.dtos.requests.PalletsRequestDto;
import com.irwi.transporters.application.mappers.ChargesMapper;
import com.irwi.transporters.application.mappers.PalletsMapper;
import com.irwi.transporters.domain.entities.Charges;
import com.irwi.transporters.domain.entities.Pallets;
import com.irwi.transporters.domain.entities.UserEntity;
import com.irwi.transporters.domain.enums.Roles;
import com.irwi.transporters.domain.enums.StatesPalets;
import com.irwi.transporters.domain.ports.service.interfaces.IPalletsService;
import com.irwi.transporters.infrastructure.persistences.PalletsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PalletsServiceImpl implements IPalletsService {

    @Autowired
    private PalletsRepository palletsRepository;

    @Override
    public Pallets create(PalletsRequestDto palletsRequestDto) {
        Pallets pallets = PalletsMapper.INSTANCE.toEntity(palletsRequestDto);

        pallets.setState(StatesPalets.AVAILABLE);
        pallets.setCurrent_capacity(palletsRequestDto.getCapacity_max());

        return palletsRepository.save(pallets);
    }

    @Override
    public void delete(Long id) {
        if(!palletsRepository.existsById(id)){
            throw new EntityNotFoundException("Pallet not found with id: " + id);
        }

        palletsRepository.deleteById(id);
    }

    @Override
    public List<PalletsResponseDto> readAll() {
        List<Pallets> palletsList = palletsRepository.findAll();

        return palletsList.stream()
                .map(PalletsMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PalletsResponseDto readById(Long id) {
        Pallets pallet = palletsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pallet not found with id: " + id));

        return PalletsMapper.INSTANCE.toResponseDto(pallet);
    }

    @Override
    public Pallets update(PalletsRequestDto palletsRequestDto, Long id) {
        return palletsRepository.findById(id)
                .map(existingProject -> {
                    Pallets pallet = PalletsMapper.INSTANCE.toEntity(palletsRequestDto);
                    pallet.setId(existingProject.getId());


                    pallet.setCurrent_capacity(palletsRequestDto.getCapacity_max());

                    if(existingProject.getCharges() != null){
                        pallet.setState(StatesPalets.IN_USE);
                    }
                    pallet.setState(StatesPalets.AVAILABLE);

                    return palletsRepository.save(pallet);
                })
                .orElseThrow(() -> new EntityNotFoundException("Pallet not found with id: " + id));
    }

    @Override
    public Set<Charges> getCharges(Long id) {
        Pallets pallets = palletsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pallet not found with id: " + id));

        return pallets.getCharges();
    }
}
