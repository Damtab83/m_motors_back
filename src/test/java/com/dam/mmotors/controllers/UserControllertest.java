package com.dam.mmotors.controllers;

import com.dam.mmotors.pojo.*;
import com.dam.mmotors.services.CarService;
import com.dam.mmotors.services.UserService;
import com.dam.mmotors.ws.UserController;
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

@WebMvcTest(UserController.class)
public class UserControllertest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllUser_shouldReturnList() throws Exception {

        BuyCar buyCar = new BuyCar();
        buyCar.setPrice(55000);

        List<Car> theCars = new ArrayList<>();
        Car myCar = new Car();
        myCar.setCar_id(22L);
        myCar.setBrand("Ford");
        myCar.setModel("Tourneo");
        myCar.setMotorization("100ecoBoost");
        myCar.setKilometer(45900);
        myCar.setFunding(true);
        myCar.setBuy(buyCar);
        theCars.add(myCar);

        User myUser = new User();
        myUser.setUser_id(22L);
        myUser.setEmail("test@test.fr");
        myUser.setPassword("helloMonTest");
        myUser.setMyCars(theCars);


        Mockito.when(userService.getAllUsers()).thenReturn(List.of(myUser));

        mockMvc.perform(get("/api/client"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].user_id").value(22L))
                .andExpect(jsonPath("$[0].email").value("test@test.fr"));
    }

    @Test
    public void getCarById_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(userService.getUserById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/client/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUserById_shouldReturn200_whenExists() throws  Exception {

        List<Car> theCars = new ArrayList<>();
        Car myCar = new Car();
        myCar.setCar_id(22L);
        myCar.setBrand("Ford");
        myCar.setModel("Tourneo");
        myCar.setMotorization("100ecoBoost");
        theCars.add(myCar);

        User myUser = new User();
        myUser.setUser_id(22L);
        myUser.setEmail("testemoi@test.fr");
        myUser.setMyCars(theCars);

        Mockito.when(userService.getUserById(22L)).thenReturn(myUser);

        mockMvc.perform(get("/api/client/22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value(22L))
                .andExpect(jsonPath("$.email").value("testemoi@test.fr"));

    }

    @Test
    public void createUser_shouldReturn201() throws Exception {
        User myUser = new User();
        myUser.setEmail("test@test.fr");
        myUser.setPassword("monMegaMotDePasse");

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(myUser)))
                .andExpect(status().isCreated());

        Mockito.verify(userService).createUser(Mockito.any(User.class));
    }

    @Test
    public void deleteUser_shouldReturn200_whenDeleted() throws Exception {
        Mockito.when(userService.deleteUserById(22L)).thenReturn(true);

        mockMvc.perform(delete("/api/client/22"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCar_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(userService.deleteUserById(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/client/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateuser_shouldReturn200() throws Exception {

        List<Car> theCars = new ArrayList<>();
        Car myCar = new Car();
        myCar.setBrand("Ford");
        myCar.setModel("Tourneo");
        myCar.setMotorization("100ecoBoost");
        myCar.setKilometer(45900);
        myCar.setFunding(true);
        theCars.add(myCar);

        User updatedUser = new User();
        updatedUser.setEmail("testemoi@testing.com");
        updatedUser.setPassword("monNouveauMotDePasse");
        updatedUser.setMyCars(theCars);

        mockMvc.perform(put("/api/client/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk());

        Mockito.verify(userService).updateUser(Mockito.eq(22L), Mockito.any(User.class));
    }
}
