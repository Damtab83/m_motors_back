package com.dam.mmotors.services;

import com.dam.mmotors.pojo.ImageCar;
import com.dam.mmotors.pojo.TestDriving;
import com.dam.mmotors.repositories.ImageCarRepository;
import com.dam.mmotors.repositories.TestDrivingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestDrivingServiceTest {

    @Mock
    private TestDrivingRepository testDrivingRepository;

    @InjectMocks
    private TestDrivingService testDrivingService;

    private TestDriving testingTestDriving;

    @BeforeEach
    public void initializeTestDriving() {
        testingTestDriving = new TestDriving();
        testingTestDriving.setTestDriving_id(11L);
        testingTestDriving.setTestDate(LocalDateTime.of(2025,12,25,12,30,00));
        testingTestDriving.setConfirmed(true);
    }

    @Test
    public void getTestDrivingById_shouldReturnTestDriving_whenExists() {
        Mockito.when(testDrivingRepository.findById(11L)).thenReturn(Optional.of(testingTestDriving));

        TestDriving result = testDrivingService.getTestDrivingById(11L);
        assertNotNull(result);
        assertEquals(LocalDateTime.of(2025,12,25,12,30,0), result.getTestDate());
        assertEquals(true, result.getConfirmed());
    }

    @Test
    public void createTestDriving_shouldCallRepositorySave() {
        testDrivingService.createTestDriving(testingTestDriving);
        Mockito.verify(testDrivingRepository).save(testingTestDriving);
    }

    @Test
    public void deleteTestDriving_shouldReturnTrue_whenExists() {
        Mockito.when(testDrivingRepository.existsById(11L)).thenReturn(true);

        Boolean result = testDrivingService.deleteTestDriving(11L);
        assertTrue(result);
        Mockito.verify(testDrivingRepository).deleteById(11L);
    }

    @Test
    public void deleteTestDriving_shouldReturnFalse_whenNotExists() {
        Mockito.when(testDrivingRepository.existsById(99L)).thenReturn(false);

        Boolean result = testDrivingService.deleteTestDriving(99L);
        assertFalse(result);
        Mockito.verify(testDrivingRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }

    @Test
    public void updateTestDriving_shouldReturnUpdatedTestDriving_whenExists() {
        TestDriving updatedTestDriving = new TestDriving();
        updatedTestDriving.setTestDate(LocalDateTime.of(2003, 8, 20,11,11,11));
        updatedTestDriving.setConfirmed(true);

        Mockito.when(testDrivingRepository.findById(11L)).thenReturn(Optional.of(testingTestDriving));
        Mockito.when(testDrivingRepository.save(Mockito.any(TestDriving.class))).thenAnswer(i ->i.getArgument(0));

        TestDriving result = testDrivingService.updateTestDriving(11L, updatedTestDriving);
        assertNotNull(result);
        assertEquals(LocalDateTime.of(2003,8,20,11,11,11), result.getTestDate());
        assertEquals(true, result.getConfirmed());
    }

    @Test
    public void updateTestDriving_shouldReturnNull_whenNotFound() {
        TestDriving updatedTestDriving = new TestDriving();
        updatedTestDriving.setConfirmed(false);

        Mockito.when(testDrivingRepository.findById(99L)).thenReturn(Optional.empty());

        TestDriving result = testDrivingService.updateTestDriving(99L, updatedTestDriving);
        assertNull(result);
    }
}
