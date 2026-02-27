package com.dam.mmotors.controllers;

import com.dam.mmotors.pojo.*;
import com.dam.mmotors.services.CarService;
import com.dam.mmotors.ws.CarController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllCar_shouldReturnList() throws Exception {

        List<ImageCar> listImages = new ArrayList<>();
        ImageCar myImage = new ImageCar();
        myImage.setName("monImage1");
        myImage.setSize(5200);
        listImages.add(myImage);

        OldCar oldCar = new OldCar();
        oldCar.setBrand("BMW");
        oldCar.setPrice(15000);

        BuyCar buyCar = new BuyCar();
        buyCar.setPrice(55000);
        buyCar.setTakeOldCar(oldCar);

        Car myCars = new Car();
        myCars.setCar_id(22L);
        myCars.setBrand("Ford");
        myCars.setModel("Tourneo");
        myCars.setMotorization("100ecoBoost");
        myCars.setKilometer(45900);
        myCars.setFunding(true);
        myCars.setBuy(buyCar);
        myCars.setRental(null);
        myCars.setTesting(null);
        myCars.setImages(listImages);


        Mockito.when(carService.getAllCars()).thenReturn(List.of(myCars));

        mockMvc.perform(get("/api/voiture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].brand").value("Ford"))
                .andExpect(jsonPath("$[0].model").value("Tourneo"))
                .andExpect(jsonPath("$[0].kilometer").value(45900));
    }

    @Test
    public void getCarById_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(carService.getCarById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/voiture/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCarById_shouldReturn200_whenExists() throws  Exception {
        Car myCar = new Car();
        myCar.setCar_id(22L);
        myCar.setModel("Tourneo");
        myCar.setBrand("Ford");
        myCar.setMotorization("90ecoBoost");

        Mockito.when(carService.getCarById(22L)).thenReturn(myCar);

        mockMvc.perform(get("/api/voiture/22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.car_id").value(22L))
                .andExpect(jsonPath("$.model").value("Tourneo"))
                .andExpect(jsonPath("$.motorization").value("90ecoBoost"))
                .andExpect(jsonPath("$.brand").value("Ford"));

    }

    @Test
    public void createCar_shouldReturn201() throws Exception {
        Car myCar = new Car();
        myCar.setBrand("Citroen");
        myCar.setModel("C4");
        myCar.setKilometer(2000);
        myCar.setMotorization("120CV");

        mockMvc.perform(post("/api/voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(myCar)))
                .andExpect(status().isCreated());

        Mockito.verify(carService).createCar(Mockito.any(Car.class));
    }

    @Test
    public void deleteCar_shouldReturn200_whenDeleted() throws Exception {
        Mockito.when(carService.deleteCar(22L)).thenReturn(true);

        mockMvc.perform(delete("/api/voiture/22"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCar_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(carService.deleteCar(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/voiture/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCar_shouldReturn200() throws Exception {
        Car updatedCar = new Car();
        updatedCar.setBrand("Kia");
        updatedCar.setModel("Picanto");
        updatedCar.setMotorization("80CV");

        mockMvc.perform(put("/api/voiture/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCar)))
                .andExpect(status().isOk());

        Mockito.verify(carService).updateCar(Mockito.eq(22L), Mockito.any(Car.class));
    }
}
