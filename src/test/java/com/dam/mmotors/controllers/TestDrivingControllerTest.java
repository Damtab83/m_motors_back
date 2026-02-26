package com.dam.mmotors.controllers;


import com.dam.mmotors.pojo.TestDriving;
import com.dam.mmotors.services.TestDrivingService;
import com.dam.mmotors.ws.TestDrivingController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestDrivingController.class)
public class TestDrivingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TestDrivingService testDrivingService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getTestDrivingById_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(testDrivingService.getTestDrivingById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/essai-routier/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTestDrivingById_shouldReturn200_whenExists() throws  Exception {
        TestDriving testDriving = new TestDriving();
        testDriving.setTestDriving_id(22L);
        testDriving.setTestDate(LocalDateTime.of(2024, 11, 11, 14, 30));
        testDriving.setConfirmed(true);

        Mockito.when(testDrivingService.getTestDrivingById(22L)).thenReturn(testDriving);

        mockMvc.perform(get("/api/essai-routier/22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testDriving_id").value(22))
                .andExpect(jsonPath("$.testDate").value("2024-11-11T14:30:00"))
                .andExpect(jsonPath("$.confirmed").value(true));


    }

    @Test
    public void createTestDriving_shouldReturn201() throws Exception {
        TestDriving testDriving = new TestDriving();
        testDriving.setTestDate(LocalDateTime.of(2026, 02, 18, 02, 15,0));
        testDriving.setConfirmed(false);

        mockMvc.perform(post("/api/essai-routier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDriving)))
                .andExpect(status().isCreated());

        Mockito.verify(testDrivingService).createTestDriving(Mockito.any(TestDriving.class));
    }

    @Test
    public void deleteTestDriving_shouldReturn200_whenDeleted() throws Exception {
        Mockito.when(testDrivingService.deleteTestDriving(22L)).thenReturn(true);

        mockMvc.perform(delete("/api/essai-routier/22"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTestDriving_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(testDrivingService.deleteTestDriving(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/essai-routier/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateTestDriving_shouldReturn200() throws Exception {
        TestDriving updatedTestDriving = new TestDriving();
        updatedTestDriving.setTestDate(LocalDateTime.of(2010, 12, 02, 12, 20,30));
        updatedTestDriving.setConfirmed(false);

        mockMvc.perform(put("/api/essai-routier/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTestDriving)))
                .andExpect(status().isOk());

        Mockito.verify(testDrivingService).updateTestDriving(Mockito.eq(22L), Mockito.any(TestDriving.class));
    }
}
