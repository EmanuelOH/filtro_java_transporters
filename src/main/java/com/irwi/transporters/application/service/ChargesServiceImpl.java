package com.irwi.transporters.application.service;

import com.irwi.transporters.application.dtos.reponses.ChargesResponseDto;
import com.irwi.transporters.application.dtos.requests.ChargesRequestDto;
import com.irwi.transporters.application.mappers.ChargesMapper;
import com.irwi.transporters.domain.entities.Charges;
import com.irwi.transporters.domain.entities.Pallets;
import com.irwi.transporters.domain.entities.UserEntity;
import com.irwi.transporters.domain.enums.Roles;
import com.irwi.transporters.domain.enums.StatesCharges;
import com.irwi.transporters.domain.enums.StatesPalets;
import com.irwi.transporters.domain.exception.MaximumFullCapacity;
import com.irwi.transporters.domain.ports.service.interfaces.IChargesService;
import com.irwi.transporters.infrastructure.persistences.ChargesRepository;
import com.irwi.transporters.infrastructure.persistences.PalletsRepository;
import com.irwi.transporters.infrastructure.persistences.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChargesServiceImpl implements IChargesService {

    @Autowired
    private ChargesRepository chargesRepository;

    @Autowired
    private PalletsRepository palletsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public Charges create(ChargesRequestDto chargesRequestDto) {

        Charges charge = ChargesMapper.INSTANCE.toEntity(chargesRequestDto);
        charge.setStates(StatesCharges.PENDING);

        Pallets pallet = palletsRepository.findById(chargesRequestDto.getPallet_id())
                .orElseThrow(() -> new EntityNotFoundException("Pallet not found"));

        pallet.setState(StatesPalets.IN_USE);

        if(pallet.getCurrent_capacity() < chargesRequestDto.getWeight()){
            throw  new MaximumFullCapacity("That weight cannot be added");
        }
        charge.setPallet(pallet);

        pallet.setCurrent_capacity(pallet.getCurrent_capacity() - chargesRequestDto.getWeight());

        palletsRepository.save(pallet);

        return chargesRepository.save(charge);
    }

    @Override
    public void delete(Long id) {
        if(!chargesRepository.existsById(id)){
            throw new EntityNotFoundException("Charge not found with id: " + id);
        }

        chargesRepository.deleteById(id);
    }

    @Override
    public List<ChargesResponseDto> readAll() {
        List<Charges> chargesList = chargesRepository.findAll();

        return chargesList.stream()
                .map(ChargesMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChargesResponseDto readById(Long id) {
        Charges charges = chargesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Charge not found with id: " + id));

        return ChargesMapper.INSTANCE.toResponseDto(charges);
    }

    @Override
    public Charges update(ChargesRequestDto chargesRequestDto, Long id) {
        return chargesRepository.findById(id)
                .map(existingProject -> {
                    Charges charge = ChargesMapper.INSTANCE.toEntity(chargesRequestDto);
                    charge.setId(existingProject.getId());
                    charge.setStates(StatesCharges.PENDING);

                    Pallets pallet = palletsRepository.findById(chargesRequestDto.getPallet_id())
                            .orElseThrow(() -> new EntityNotFoundException("Pallet not found"));

                    if(pallet.getCurrent_capacity() < chargesRequestDto.getWeight()){
                        throw  new MaximumFullCapacity("That weight cannot be added");
                    }
                    charge.setPallet(pallet);

                    pallet.setCurrent_capacity(pallet.getCurrent_capacity() - chargesRequestDto.getWeight());

                    palletsRepository.save(pallet);

                    return chargesRepository.save(charge);
                })
                .orElseThrow(() -> new EntityNotFoundException("Charge not found with id: " + id));
    }

    @Override
    public Charges assignUser(Long idCharges, Long idUsers) {
        Charges charges = chargesRepository.findById(idCharges)
                .orElseThrow(() -> new EntityNotFoundException("Charge with id " + idCharges + "not found"));

        UserEntity users = userRepository.findById(idUsers)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + idUsers + "not found"));

        charges.setUser(users);

        return chargesRepository.save(charges);
    }

    @Override
    public List<ChargesResponseDto> getCharges() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity currentUser = (UserEntity) userDetails;

        List<Charges> chargesList;

        if (currentUser.getRole().equals(Roles.ADMIN)) {
            chargesList = chargesRepository.findAll();
        } else {
            chargesList = chargesRepository.findByUser(currentUser);
        }

        return chargesList.stream()
                .map(ChargesMapper.INSTANCE::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Charges changeStatus(StatesCharges state, Long id) {
        return chargesRepository.findById(id)
                .map(existingProject -> {
                    try {
                        existingProject.setStates(state);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid state value: " + state);
                    }
                    return chargesRepository.save(existingProject);
                })
                .orElseThrow(() -> new EntityNotFoundException("Charge not found with id: " + id));
    }

    @Override
    public void sendReportDamage(Long id, String messageContent){
        SimpleMailMessage message = new SimpleMailMessage();

        Charges charges = chargesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Charge not found with id: " + id));

        UserEntity user = charges.getUser();

        message.setTo("osorioemanuel0520@gmail.com");
        message.setSubject("Nuevo reporte de daño con la carga con el id: " + id);
        message.setText(messageContent);

        emailSender.send(message);
    }
}
