package com.dam.mmotors.controllers;


import com.dam.mmotors.ws.OldCarController;
import com.dam.mmotors.pojo.OldCar;
import com.dam.mmotors.services.OldCarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(OldCarController.class)
public class OldCarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OldCarService oldCarService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllOldCars_shouldReturnList() throws Exception {

        OldCar car1 = new OldCar();
        car1.setOldCar_id(3L);
        car1.setBrand("Ford");

        OldCar car2 = new OldCar();
        car2.setOldCar_id(4L);
        car2.setBrand("BMW");

        Mockito.when(oldCarService.getAllOldCars()).thenReturn(List.of(car1, car2));

        mockMvc.perform(get("/api/ancienne-voiture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].brand").value("Ford"))
                .andExpect(jsonPath("$[1].brand").value("BMW"));
    }

    @Test
    public void getOldCarById_shouldReturn404_whenNotFound() throws Exception {

        Mockito.when(oldCarService.getOldCarById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/ancienne-voiture/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOldCarById_shouldReturnCar_whenExists() throws Exception {

        OldCar car = new OldCar();
        car.setOldCar_id(11L);
        car.setBrand("Tesla");
        car.setPrice(25000);

        Mockito.when(oldCarService.getOldCarById(11L)).thenReturn(Optional.of(car));

        mockMvc.perform(get("/api/ancienne-voiture/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.oldCar_id").value(11))
                .andExpect(jsonPath("$.brand").value("Tesla"))
                .andExpect(jsonPath("$.price").value(25000));
    }

    @Test
    public void createOldCar_shouldReturn201() throws Exception {

        OldCar car = new OldCar();
        car.setBrand("Tesla");
        car.setPrice(33000);

        mockMvc.perform(post("/api/ancienne-voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isCreated());
        Mockito.verify(oldCarService).createOldCar(Mockito.any(OldCar.class));
    }

    @Test
    public void deleteOldCar_shouldReturn200_whenOldCarDeleted() throws Exception {

        Mockito.when(oldCarService.deleteOldCarById(11L)).thenReturn(true);

        mockMvc.perform(delete("/api/ancienne-voiture/11"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOldCar_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(oldCarService.deleteOldCarById(99L)).thenReturn(false);
        mockMvc.perform(delete("/api/ancienne-voiture/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOldCar_shouldReturn200() throws Exception {

        OldCar car = new OldCar();
        car.setBrand("CITROEN");
        car.setPrice(15680);

        mockMvc.perform(put("/api/ancienne-voiture/11")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk());

        Mockito.verify(oldCarService).updateOldCar(Mockito.eq(11L), Mockito.any(OldCar.class));
    }

}
