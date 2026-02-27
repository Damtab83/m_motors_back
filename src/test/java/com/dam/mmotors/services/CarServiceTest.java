package com.dam.mmotors.services;

import com.dam.mmotors.pojo.Car;
import com.dam.mmotors.pojo.OldCar;
import com.dam.mmotors.repositories.CarRepository;
import com.dam.mmotors.repositories.OldCarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;


    private Car testingCar;

    @BeforeEach
    public void initializeCar() {
        testingCar =  new Car();
        testingCar.setCar_id(11L);
        testingCar.setBrand("Tesla");
        testingCar.setModel("Model3");
        testingCar.setMotorization("Electrique");
        testingCar.setKilometer(1500);
    }

    @AfterEach
    public void eraseCar() {
        testingCar = null;
    }

    @Test
    public void getCarById_shouldReturnCar_whenExists() {

        Mockito.when(carRepository.findById(11L)).thenReturn(Optional.of(testingCar));

        Optional<Car> result = Optional.ofNullable(carService.getCarById(11L));

        assertTrue(result.isPresent());

        Car myCar = result.get();
        assertEquals(11L, myCar.getCar_id());
        assertEquals("Tesla", myCar.getBrand());
        assertEquals("Model3", myCar.getModel());
        assertEquals("Electrique", myCar.getMotorization());
        assertEquals(1500, myCar.getKilometer());
    }

    @Test
    public void createCar_shouldCallRepositorySave() {

        carService.createCar(testingCar);

        Mockito.verify(carRepository, Mockito.times(1))
                .save(testingCar);
    }

    @Test
    public void deleteCar_shouldCallRepositoryDelete() {
        Mockito.when(carRepository.existsById(11L)).thenReturn(true);
        carService.deleteCar(11L);

        Mockito.verify(carRepository, Mockito.times(1))
                .deleteById(11L);
    }

    @Test
    public void updateCar_shouldUpdateAndSaveCar() {

        Car updatedCar = new Car();
        updatedCar.setBrand("BMW");
        updatedCar.setModel("Model2");

        Mockito.when(carRepository.findById(11L))
                .thenReturn(Optional.of(testingCar));

        Mockito.when(carRepository.save(Mockito.any(Car.class)))
                .thenAnswer(i -> i.getArgument(0));

        Car result = carService.updateCar(11L, updatedCar);

        assertEquals("BMW", result.getBrand());
        assertEquals("Model2", result.getModel());

        Mockito.verify(carRepository).save(testingCar);
    }

    @Test
    public void updateCar_shouldThrowException_whenCarNotFound() {

        Mockito.when(carRepository.findById(11L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> carService.updateCar(11L, new Car())
        );

        assertEquals("Car not found", exception.getMessage());
    }
}
