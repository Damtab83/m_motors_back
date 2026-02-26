package com.dam.mmotors.services;

import com.dam.mmotors.pojo.BuyCar;
import com.dam.mmotors.pojo.OldCar;
import com.dam.mmotors.repositories.BuyCarRepository;
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
public class BuyCarServiceTest {

    @Mock
    private BuyCarRepository buyCarRepository;

    @InjectMocks
    private BuyCarService buyCarService;

    private BuyCar testingBuyCar;

    @BeforeEach
    public void initializeBuyCar() {
        OldCar oldCar = new OldCar();
        oldCar.setOldCar_id(22L);
        oldCar.setBrand("BMW");
        oldCar.setPrice(10000);
        
        testingBuyCar = new BuyCar();
        testingBuyCar.setBuyCar_id(11L);
        testingBuyCar.setPrice(55000);
        testingBuyCar.setTakeOldCar(oldCar);
    }

    @Test
    public void getAllBuyCar_shouldReturnList() {
        Mockito.when(buyCarRepository.findAll()).thenReturn(List.of(testingBuyCar));

        List<BuyCar> result = buyCarService.getAllBuyCar();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(11L, result.get(0).getBuyCar_id());
    }

    @Test
    public void getBuyCarById_shouldReturnBuyCar_whenExists() {
        Mockito.when(buyCarRepository.findById(11L)).thenReturn(Optional.of(testingBuyCar));

        BuyCar result = buyCarService.getBuyCarById(11L);
        assertNotNull(result);
        assertEquals(55000, result.getPrice());
        assertTrue(result.hasTradeIn());
    }

    @Test
    public void createBuyCar_shouldCallRepositorySave() {
        buyCarService.createBuyCar(testingBuyCar);
        Mockito.verify(buyCarRepository).save(testingBuyCar);
    }

    @Test
    public void deleteBuyCar_shouldReturnTrue_whenExists() {
        Mockito.when(buyCarRepository.existsById(11L)).thenReturn(true);

        Boolean result = buyCarService.deleteBuyCar(11L);
        assertTrue(result);
        Mockito.verify(buyCarRepository).deleteById(11L);
    }

    @Test
    public void deleteBuyCar_shouldReturnFalse_whenNotExists() {
        Mockito.when(buyCarRepository.existsById(99L)).thenReturn(false);

        Boolean result = buyCarService.deleteBuyCar(99L);
        assertFalse(result);
        Mockito.verify(buyCarRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }

    @Test
    public void updateBuyCar_shouldReturnUpdatedBuyCar_whenExists() {
        BuyCar updatedCar = new BuyCar();
        updatedCar.setPrice(85000);
        updatedCar.setTakeOldCar(null);

        Mockito.when(buyCarRepository.findById(11L)).thenReturn(Optional.of(testingBuyCar));
        Mockito.when(buyCarRepository.save(Mockito.any(BuyCar.class))).thenAnswer(i ->i.getArgument(0));

        BuyCar result = buyCarService.updateBuyCar(11L, updatedCar);
        assertNotNull(result);
        assertEquals(85000, result.getPrice());
        assertNull(result.getTakeOldCar());
    }

    @Test
    public void updateBuyCar_shouldReturnNull_whenNotFound() {
        BuyCar updatedCar = new BuyCar();
        updatedCar.setPrice(85000);

        Mockito.when(buyCarRepository.findById(99L)).thenReturn(Optional.empty());

        BuyCar result = buyCarService.updateBuyCar(99L, updatedCar);
        assertNull(result);
    }

}
