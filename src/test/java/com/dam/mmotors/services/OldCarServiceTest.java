package com.dam.mmotors.services;

import com.dam.mmotors.pojo.OldCar;
import com.dam.mmotors.repositories.OldCarRepository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OldCarServiceTest {

    @Mock
    private OldCarRepository oldCarRepository;

    @InjectMocks
    private OldCarService oldCarService;


    private OldCar testingOldCar;

        @BeforeEach
        public void initializeOldCar() {
            testingOldCar =  new OldCar();
            testingOldCar.setOldCar_id(11L);
            testingOldCar.setBrand("Tesla");
            testingOldCar.setPrice(25000);
        }

        @AfterEach
        public void eraseOldCar() {
            testingOldCar = null;
        }

        @Test
        public void getOldCarById_shouldReturnOldCar_whenExists() {

            Mockito.when(oldCarRepository.findById(11L)).thenReturn(Optional.of(testingOldCar));

            Optional<OldCar> result = oldCarService.getOldCarById(11L);

            assertTrue(result.isPresent());

            OldCar car = result.get();
            assertEquals(11L, car.getOldCar_id());
            assertEquals("Tesla", car.getBrand());
            assertEquals(25000, car.getPrice());
        }

        @Test
        public void createOldCar_shouldCallRepositorySave() {

            oldCarService.createOldCar(testingOldCar);

            Mockito.verify(oldCarRepository, Mockito.times(1))
                    .save(testingOldCar);
        }

        @Test
        public void deleteOldCar_shouldCallRepositoryDelete() {
            Mockito.when(oldCarRepository.existsById(11L)).thenReturn(true);
            oldCarService.deleteOldCarById(11L);

            Mockito.verify(oldCarRepository, Mockito.times(1))
                    .deleteById(11L);
        }

    @Test
    public void updateOldCar_shouldUpdateAndSaveCar() {

        OldCar updatedCar = new OldCar();
        updatedCar.setBrand("BMW");
        updatedCar.setPrice(30000);

        // Simulation : la voiture existe déjà
        Mockito.when(oldCarRepository.findById(11L))
                .thenReturn(Optional.of(testingOldCar));

        Mockito.when(oldCarRepository.save(Mockito.any(OldCar.class)))
                .thenAnswer(i -> i.getArgument(0)); // renvoie l'objet sauvegardé

        // Act
        OldCar result = oldCarService.updateOldCar(11L, updatedCar);

        // Assert
        assertEquals("BMW", result.getBrand());
        assertEquals(30000, result.getPrice());

        Mockito.verify(oldCarRepository).save(testingOldCar);
    }

    @Test
    public void updateOldCar_shouldThrowException_whenCarNotFound() {

        Mockito.when(oldCarRepository.findById(11L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> oldCarService.updateOldCar(11L, new OldCar())
        );

        assertEquals("OldCar not found", exception.getMessage());
    }

}

