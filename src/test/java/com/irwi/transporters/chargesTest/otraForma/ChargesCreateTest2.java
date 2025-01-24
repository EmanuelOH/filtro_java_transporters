package com.irwi.transporters.chargesTest.otraForma;

import com.irwi.transporters.application.dtos.requests.ChargesRequestDto;
import com.irwi.transporters.application.service.ChargesServiceImpl;
import com.irwi.transporters.domain.entities.Charges;
import com.irwi.transporters.domain.entities.Pallets;
import com.irwi.transporters.domain.exception.MaximumFullCapacity;
import com.irwi.transporters.infrastructure.persistences.ChargesRepository;
import com.irwi.transporters.infrastructure.persistences.PalletsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChargesCreateTest2 {
    // Mock: Simula el comportamiento del repositorio
    @Mock
    private ChargesRepository chargesRepository;

    // Mock: Simula el comportamiento del repositorio
    @Mock
    private PalletsRepository palletsRepository;

    // InjectMock: Inyecta los mock en el servicio
    @InjectMocks
    private ChargesServiceImpl chargesService;


    // Inicializa el mock antes de cada prueba
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    // Este metodo verifica si la creacion es exitosa
    @Test
    void testCreateCharge_Success(){
        //Crea un dto con los datos necesarios para realizar el test
        ChargesRequestDto requestDto = createMockRequest(1L, 20);

        //Crea un pallet simula con capacidad suficiente
        Pallets pallets = createMockPallet(1L, 50L, 100L);

        //Crea un charges simulado que sera devuelto por el repositorio
        Charges savedCharge = createMockCharge(1L);

        // Configura la configuracion del comportamiento a la base de datos
        when(palletsRepository.findById(1L)).thenReturn(Optional.of(pallets));
        when(chargesRepository.save(any(Charges.class))).thenReturn(savedCharge);

        // Llama el metodo del servicio para el test
        Charges result = chargesService.create(requestDto);

        // Verifica que el resultado no sea nulo
        assertNotNull(result);

        // Verifica que el id que se espera sea el correcto
        assertEquals(1L, result.getId());

        //Verifica que el repositorio de cargos haya sido llamado una vez con cualquier instacia de charges
        verify(chargesRepository, times(1)).save(any(Charges.class));
    }

    // Este metodo verifica que se lanza un excepcion cuando el pallet no se encuentra en la base de datos
    @Test
    void testCreateCharges_PalletNotFound(){
        //Crea un dto con los datos necesarios para realizar el test
        ChargesRequestDto requestDto = createMockRequest(1L, 50);

        // Configura la simulación de un pallet que no existe en la base de datos
        when(palletsRepository.findById(1L)).thenReturn(Optional.empty());

        //Verifica que se lanza una excepcion EntityNotFoundException cuando intentamos crear el cargo
        assertThrows(EntityNotFoundException.class, () -> chargesService.create(requestDto));

        // Verifica que el método save no haya sido llamado
        verify(chargesRepository, never()).save(any(Charges.class));
    }

    //este metodo verifica que se lanza una excepción cuando el peso del cargo excede la capacidad del pallet.
    @Test
    void testCreateCharge_ExceedsCapacity(){
        // Crea un DTO con los datos necesarios para el test
        ChargesRequestDto request = createMockRequest(1L, 150); // El peso del cargo excede la capacidad del pallet

        // Crea un pallet simulado con capacidad insuficiente
        Pallets pallet = createMockPallet(1L, 90L, 100L); // ID, carga actual y capacidad máxima

        // Configura la simulación de un pallet que existe en la base de datos
        when(palletsRepository.findById(1L)).thenReturn(java.util.Optional.of(pallet)); // El pallet existe

        // Verifica que se lanza una excepción MaximumFullCapacity cuando el peso del cargo excede la capacidad
        assertThrows(MaximumFullCapacity.class, () -> chargesService.create(request));

        // Verifica que el método save no haya sido llamado
        verify(chargesRepository, never()).save(any(Charges.class));
    }

    private ChargesRequestDto createMockRequest(Long palletId, long weight) {
        ChargesRequestDto request = new ChargesRequestDto();
        request.setPallet_id(palletId); // Asigna el ID del pallet
        request.setWeight(weight); // Asigna el peso del cargo
        return request;
    }

    /**
     * Crea un objeto Pallet simulado con los datos de prueba.
     */
    private Pallets createMockPallet(Long id, Long currentLoad, Long maxCapacity) {
        Pallets pallet = new Pallets();
        pallet.setId(id); // Asigna el ID del pallet
        pallet.setCurrent_capacity(currentLoad); // Asigna la carga actual
        pallet.setCapacity_max(maxCapacity); // Asigna la capacidad máxima
        return pallet;
    }

    /**
     * Crea un objeto Charges simulado con los datos de prueba.
     */
    private Charges createMockCharge(Long id) {
        Charges charge = new Charges();
        charge.setId(id); // Asigna el ID del cargo
        return charge;
    }
}
