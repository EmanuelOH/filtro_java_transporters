package com.irwi.transporters.chargesTest;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita el uso de Mockito en esta clase de prueba
public class ChargesCreateTest {

    // Simula los repositorios para los test
    @Mock
    private ChargesRepository chargesRepository;

    // Mock de PalletsRepository, la clase que maneja las operaciones de la base de datos relacionadas con Pallets
    @Mock
    private PalletsRepository palletsRepository;

    // La clase bajo prueba, a la que se inyectan los mocks de arriba
    @InjectMocks
    private ChargesServiceImpl chargesService;

    // Variables de prueba que serán utilizadas en cada test
    private ChargesRequestDto chargesRequestDto;
    private Pallets pallet;
    private Charges charge;

    @BeforeEach // Esta anotación indica que el método se ejecutará antes de cada prueba
    void setUp() {
        // Se inicializan los objetos con valores que serán reutilizados en los tests
        chargesRequestDto = new ChargesRequestDto();

        chargesRequestDto.setPallet_id(1L); // Establece el id del pallet

        chargesRequestDto.setWeight(50L); // Establece el peso de la carga

        pallet = new Pallets();
        pallet.setId(1L); // Establece el id del pallet
        pallet.setCurrent_capacity(100L);  // Establece una capacidad suficiente para contener el peso

        charge = new Charges();
        charge.setId(1L); // Establece el id de la carga
    }

    @Test // Valida si se crea correctamente el charge
    void testCreate_Success() {
        // Simula la existencia de un pallet con ID 1
        when(palletsRepository.findById(1L)).thenReturn(Optional.of(pallet));

        // Simula que el método save en el repositorio de Charges guarda correctamente la carga
        when(chargesRepository.save(any(Charges.class))).thenReturn(charge);

        // Llama al método bajo prueba
        Charges result = chargesService.create(chargesRequestDto);

        // Verifica que el resultado no sea nulo, lo cual indica que la creación fue exitosa
        assertNotNull(result);

        // Verifica que el ID del charge sea el esperado
        assertEquals(1L, result.getId());

        // Verifica que el repositorio de charges haya sido llamado exactamente una vez con cualquier instancia de Charges
        verify(chargesRepository, times(1)).save(any(Charges.class));
    }

    @Test // Valida cuando se le manda la excepcion por si supero la carga maxima
    void testCreate_Fail_ExceedsCapacity() {
        // Establece un peso que excede la capacidad del pallet
        chargesRequestDto.setWeight(150L);  // Excede la capacidad máxima del pallet

        // Simula la existencia de un pallet con ID 1
        when(palletsRepository.findById(1L)).thenReturn(Optional.of(pallet));

        // Verifica que se lanza la excepción MaximumFullCapacity
        assertThrows(MaximumFullCapacity.class, () -> chargesService.create(chargesRequestDto));

        // Verifica que el repositorio de charges no haya sido llamado
        verify(chargesRepository, never()).save(any(Charges.class));
    }

    @Test // Valida si encuentra el pallet
    void testCreate_PalletNotFound() {
        // Simula que no se encuentra un pallet con ID 1
        when(palletsRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica que se lanza la excepción EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> chargesService.create(chargesRequestDto));

        // Verifica que el repositorio de charges no haya sido llamado
        verify(chargesRepository, never()).save(any(Charges.class));
    }

    @Test // Una prueba de precion de crear muchas charges
    void testCreate_PressureTest_FillPalletToMaxCapacity() {
        long maxCapacity = pallet.getCurrent_capacity();  // Obtener la capacidad máxima del pallet

        // Hacemos que el pallet reciba varias cargas hasta que alcance la capacidad máxima
        long totalWeight = 0;
        int numOfCharges = 10;  // Número de cargas que intentaremos agregar

        // Simulamos múltiples cargas y verificamos que la capacidad no se exceda
        for (int i = 0; i < numOfCharges; i++) {
            // En cada carga, incrementamos el peso de la carga en 10 (solo como ejemplo)
            long weight = 10;
            totalWeight += weight;

            // Verifica que el totalWeight no supere la capacidad máxima
            if (totalWeight > maxCapacity) {
                assertThrows(MaximumFullCapacity.class, () -> {
                    chargesRequestDto.setWeight(weight);
                    chargesService.create(chargesRequestDto);  // Intenta agregar la carga
                });
            } else {
                System.out.println("Guardando carga: " + (i+1));
                // Si el peso total no supera la capacidad, intentamos crear la carga
                chargesRequestDto.setWeight(weight);
                when(palletsRepository.findById(1L)).thenReturn(Optional.of(pallet));
                when(chargesRepository.save(any(Charges.class))).thenReturn(charge);

                Charges result = chargesService.create(chargesRequestDto);

                assertNotNull(result);
                assertEquals(1L, result.getId());
            }
        }

        // Verificamos que el repositorio de charges fue llamado tantas veces como el número de cargas
        verify(chargesRepository, times(numOfCharges)).save(any(Charges.class));
    }

}
