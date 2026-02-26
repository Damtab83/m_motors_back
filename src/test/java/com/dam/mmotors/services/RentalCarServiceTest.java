package com.dam.mmotors.services;

import com.dam.mmotors.pojo.BuyCar;
import com.dam.mmotors.pojo.RentalCar;
import com.dam.mmotors.repositories.RentalCarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RentalCarServiceTest {

    @Mock
    private RentalCarRepository rentalCarRepository;

    @InjectMocks
    private RentalCarService rentalCarService;

    private RentalCar testingRentalCar;
    
    @BeforeEach
    public void initializeRentalCar() {
        testingRentalCar = new RentalCar();
        testingRentalCar.setRentalCar_id(11L);
        testingRentalCar.setPrice(45600);
        testingRentalCar.setRentalSubscription(true);
    }

    @Test
    public void getAllRentalCar_shouldReturnList() {
        Mockito.when(rentalCarRepository.findAll()).thenReturn(List.of(testingRentalCar));

        List<RentalCar> result = rentalCarService.getAllRentalCar();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(11L, result.get(0).getRentalCar_id());
    }

    @Test
    public void getRentalCarById_shouldReturnRentalCar_whenExists() {
        Mockito.when(rentalCarRepository.findById(11L)).thenReturn(Optional.of(testingRentalCar));

        RentalCar result = rentalCarService.getRentalCarById(11L);
        assertNotNull(result);
        assertEquals(45600, result.getPrice());
        assertTrue(result.getRentalSubscription());
    }

    @Test
    public void createRentalCar_shouldCallRepositorySave() {
        rentalCarService.createRentalCar(testingRentalCar);
        Mockito.verify(rentalCarRepository).save(testingRentalCar);
    }

    @Test
    public void deleteRentalCar_shouldReturnTrue_whenExists() {
        Mockito.when(rentalCarRepository.existsById(11L)).thenReturn(true);

        Boolean result = rentalCarService.deleteRentalCar(11L);
        assertTrue(result);
        Mockito.verify(rentalCarRepository).deleteById(11L);
    }

    @Test
    public void deleteRentalCar_shouldReturnFalse_whenNotExists() {
        Mockito.when(rentalCarRepository.existsById(99L)).thenReturn(false);

        Boolean result = rentalCarService.deleteRentalCar(99L);
        assertFalse(result);
        Mockito.verify(rentalCarRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }

    @Test
    public void updateRentalCar_shouldReturnUpdatedRentalCar_whenExists() {
        RentalCar updatedRentalCar = new RentalCar();
        updatedRentalCar.setPrice(85000);
        updatedRentalCar.setRentalSubscription(true);

        Mockito.when(rentalCarRepository.findById(11L)).thenReturn(Optional.of(testingRentalCar));
        Mockito.when(rentalCarRepository.save(Mockito.any(RentalCar.class))).thenAnswer(i ->i.getArgument(0));

        RentalCar result = rentalCarService.updateRentalCar(11L, updatedRentalCar);
        assertNotNull(result);
        assertEquals(85000, result.getPrice());
        assertTrue(result.getRentalSubscription());
    }

    @Test
    public void updateRentalCar_shouldReturnNull_whenNotFound() {
        RentalCar updatedRentalCar = new RentalCar();
        updatedRentalCar.setPrice(85000);

        Mockito.when(rentalCarRepository.findById(99L)).thenReturn(Optional.empty());

        RentalCar result = rentalCarService.updateRentalCar(99L, updatedRentalCar);
        assertNull(result);
    }
}
