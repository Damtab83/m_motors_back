package com.dam.mmotors.controllers;

import com.dam.mmotors.pojo.ImageCar;
import com.dam.mmotors.services.ImageCarService;
import com.dam.mmotors.ws.ImageCarController;
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

@WebMvcTest(ImageCarController.class)
public class ImageCarControllertest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ImageCarService imageCarService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllImageCar_shouldReturnList() throws Exception {

        ImageCar imageCar = new ImageCar();
        imageCar.setImageCar_id(22L);
        imageCar.setName("monImage1");
        imageCar.setSize(23456);

        Mockito.when(imageCarService.getAllImageCar()).thenReturn(List.of(imageCar));

        mockMvc.perform(get("/api/image-voiture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].size").value(23456));
    }

    @Test
    public void getImageCarById_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(imageCarService.getImageCarById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/image-voiture/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getImageCarById_shouldReturn200_whenExists() throws  Exception {
        ImageCar imageCar = new ImageCar();
        imageCar.setImageCar_id(22L);
        imageCar.setName("monImage1");
        imageCar.setSize(23456);

        Mockito.when(imageCarService.getImageCarById(22L)).thenReturn(imageCar);

        mockMvc.perform(get("/api/image-voiture/22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imageCar_id").value(22))
                .andExpect(jsonPath("$.size").value(23456));

    }

    @Test
    public void createImageCar_shouldReturn201() throws Exception {
        ImageCar imageCar = new ImageCar();
        imageCar.setName("monImage2");
        imageCar.setSize(34567);

        mockMvc.perform(post("/api/image-voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imageCar)))
                .andExpect(status().isCreated());

        Mockito.verify(imageCarService).createImageCar(Mockito.any(ImageCar.class));
    }

    @Test
    public void deleteImageCar_shouldReturn200_whenDeleted() throws Exception {
        Mockito.when(imageCarService.deleteImageCar(22L)).thenReturn(true);

        mockMvc.perform(delete("/api/image-voiture/22"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteImageCar_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(imageCarService.deleteImageCar(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/image-voiture/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateImageCar_shouldReturn200() throws Exception {
        ImageCar updatedImageCar = new ImageCar();
        updatedImageCar.setName("monImage3");
        updatedImageCar.setSize(44444);

        mockMvc.perform(put("/api/image-voiture/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedImageCar)))
                .andExpect(status().isOk());

        Mockito.verify(imageCarService).updateImageCar(Mockito.eq(22L), Mockito.any(ImageCar.class));
    }
}
