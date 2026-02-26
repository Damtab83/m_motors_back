package com.dam.mmotors.controllers;

import com.dam.mmotors.pojo.RentalCar;
import com.dam.mmotors.services.RentalCarService;
import com.dam.mmotors.ws.RentalCarController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RentalCarController.class)
public class RentalCarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RentalCarService rentalCarService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllRentalCar_shouldReturnList() throws Exception {

        RentalCar rentalCar = new RentalCar();
        rentalCar.setRentalCar_id(22L);
        rentalCar.setPrice(22000);
        rentalCar.setRentalSubscription(true);

        Mockito.when(rentalCarService.getAllRentalCar()).thenReturn(List.of(rentalCar));

        mockMvc.perform(get("/api/location-voiture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].price").value(22000));
    }

    @Test
    public void getRentalCarById_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(rentalCarService.getRentalCarById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/location-voiture/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRentalCarById_shouldReturn200_whenExists() throws  Exception {
        RentalCar rentalCar = new RentalCar();
        rentalCar.setRentalCar_id(22L);
        rentalCar.setPrice(22000);

        Mockito.when(rentalCarService.getRentalCarById(22L)).thenReturn(rentalCar);

        mockMvc.perform(get("/api/location-voiture/22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rentalCar_id").value(22))
                .andExpect(jsonPath("$.price").value(22000));

    }

    @Test
    public void createRentalCar_shouldReturn201() throws Exception {
        RentalCar rentalCar = new RentalCar();
        rentalCar.setPrice(65900);
        rentalCar.setRentalSubscription(true);

        mockMvc.perform(post("/api/location-voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentalCar)))
                .andExpect(status().isCreated());

        Mockito.verify(rentalCarService).createRentalCar(Mockito.any(RentalCar.class));
    }

    @Test
    public void deleteRentalCar_shouldReturn200_whenDeleted() throws Exception {
        Mockito.when(rentalCarService.deleteRentalCar(22L)).thenReturn(true);

        mockMvc.perform(delete("/api/location-voiture/22"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRentalCar_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(rentalCarService.deleteRentalCar(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/location-voiture/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateRentalCar_shouldReturn200() throws Exception {
        RentalCar updatedRentalCar = new RentalCar();
        updatedRentalCar.setPrice(55900);
        updatedRentalCar.setRentalSubscription(false);

        mockMvc.perform(put("/api/location-voiture/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRentalCar)))
                .andExpect(status().isOk());

        Mockito.verify(rentalCarService).updateRentalCar(Mockito.eq(22L), Mockito.any(RentalCar.class));
    }

}
