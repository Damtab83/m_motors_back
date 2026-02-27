package com.dam.mmotors.services;

import com.dam.mmotors.pojo.Car;
import com.dam.mmotors.pojo.Role;
import com.dam.mmotors.pojo.User;
import com.dam.mmotors.repositories.CarRepository;
import com.dam.mmotors.repositories.UserRepository;
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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    private User testingUser;

    @BeforeEach
    public void initializeUser() {
        testingUser =  new User();
        testingUser.setUser_id(11L);
        testingUser.setEmail("testeur@test.com");
        testingUser.setPassword("motDePasseInitialisé");
        testingUser.setRole(Role.ADMIN);
        testingUser.setMyCars(null);
    }

    @AfterEach
    public void eraseUser() {
        testingUser = null;
    }

    @Test
    public void getUserById_shouldReturnUser_whenExists() {

        Mockito.when(userRepository.findById(11L)).thenReturn(Optional.of(testingUser));

        Optional<User> result = Optional.ofNullable(userService.getUserById(11L));

        assertTrue(result.isPresent());

        User myUser = result.get();
        assertEquals(11L, myUser.getUser_id());
        assertEquals("testeur@test.com", myUser.getEmail());
        assertEquals("motDePasseInitialisé", myUser.getPassword());
        assertEquals(Role.CUSTOMER, myUser.getRole());
    }

    @Test
    public void createUser_shouldCallRepositorySave() {

        userService.createUser(testingUser);

        Mockito.verify(userRepository, Mockito.times(1))
                .save(testingUser);
    }

    @Test
    public void deleteUser_shouldCallRepositoryDelete() {
        Mockito.when(userRepository.existsById(11L)).thenReturn(true);
        userService.deleteUserById(11L);

        Mockito.verify(userRepository, Mockito.times(1))
                .deleteById(11L);
    }

    @Test
    public void updateUser_shouldUpdateAndSaveUser() {

        User updatedUser = new User();
        updatedUser.setEmail("test@test.fr");
        updatedUser.setRole(Role.ADMIN);

        Mockito.when(userRepository.findById(11L))
                .thenReturn(Optional.of(testingUser));

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArgument(0));

        User result = userService.updateUser(11L, updatedUser);

        assertEquals("test@test.fr", result.getEmail());
        assertEquals(Role.CUSTOMER, result.getRole());

        Mockito.verify(userRepository).save(testingUser);
    }

    @Test
    public void updateUser_shouldThrowException_whenUserNotFound() {

        Mockito.when(userRepository.findById(11L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.updateUser(11L, new User())
        );

        assertEquals("User not found", exception.getMessage());
    }
}
