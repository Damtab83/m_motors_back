package com.dam.mmotors.controllers;

import com.dam.mmotors.pojo.BuyCar;
import com.dam.mmotors.pojo.OldCar;
import com.dam.mmotors.services.BuyCarService;
import com.dam.mmotors.ws.BuyCarController;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BuyCarController.class)
public class BuyCarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BuyCarService buyCarService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllBuyCar_shouldReturnList() throws Exception {
        OldCar oldCar = new OldCar();
        oldCar.setOldCar_id(5L);

        BuyCar car1 = new BuyCar();
        car1.setBuyCar_id(22L);
        car1.setPrice(22000);
        car1.setTakeOldCar(oldCar);

        Mockito.when(buyCarService.getAllBuyCar()).thenReturn(List.of(car1));

        mockMvc.perform(get("/api/achat-voiture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].price").value(22000));
    }

    @Test
    public void getBuyCarById_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(buyCarService.getBuyCarById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/achat-voiture/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBuyCarById_shouldReturn200_whenExists() throws  Exception {
        OldCar oldCar = new OldCar();
        oldCar.setOldCar_id(5L);

        BuyCar car = new BuyCar();
        car.setBuyCar_id(22L);
        car.setPrice(22000);
        car.setTakeOldCar(oldCar);

        Mockito.when(buyCarService.getBuyCarById(22L)).thenReturn(car);

        mockMvc.perform(get("/api/achat-voiture/22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buyCar_id").value(22))
                .andExpect(jsonPath("$.price").value(22000));

    }

    @Test
    public void createBuyCar_shouldReturn201() throws Exception {
        BuyCar buyCar = new BuyCar();
        buyCar.setPrice(65900);

        mockMvc.perform(post("/api/achat-voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buyCar)))
                .andExpect(status().isCreated());

        Mockito.verify(buyCarService).createBuyCar(Mockito.any(BuyCar.class));
    }

    @Test
    public void deleteBuyCar_shouldReturn200_whenDeleted() throws Exception {
        Mockito.when(buyCarService.deleteBuyCar(22L)).thenReturn(true);

        mockMvc.perform(delete("/api/achat-voiture/22"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBuyCar_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(buyCarService.deleteBuyCar(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/achat-voiture/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBuyCar_shouldReturn200() throws Exception {
        BuyCar updatedCar = new BuyCar();
        updatedCar.setPrice(55900);

        mockMvc.perform(put("/api/achat-voiture/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCar)))
                .andExpect(status().isOk());

        Mockito.verify(buyCarService).updateBuyCar(Mockito.eq(22L), Mockito.any(BuyCar.class));
    }
}
