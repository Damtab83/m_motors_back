package com.dam.mmotors.services;

import com.dam.mmotors.pojo.ImageCar;
import com.dam.mmotors.repositories.ImageCarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ImageCarServiceTest {

    @Mock
    private ImageCarRepository imageCarRepository;

    @InjectMocks
    private ImageCarService imageCarService;

    private ImageCar testingImageCar;

    @BeforeEach
    public void initializeImageCar() {
        testingImageCar = new ImageCar();
        testingImageCar.setImageCar_id(11L);
        testingImageCar.setName("monImage1");
        testingImageCar.setSize(23456);
    }

    @Test
    public void getAllImageCar_shouldReturnList() {
        Mockito.when(imageCarRepository.findAll()).thenReturn(List.of(testingImageCar));

        List<ImageCar> result = imageCarService.getAllImageCar();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(11L, result.get(0).getImageCar_id());
    }

    @Test
    public void getImageCarById_shouldReturnRentalCar_whenExists() {
        Mockito.when(imageCarRepository.findById(11L)).thenReturn(Optional.of(testingImageCar));

        ImageCar result = imageCarService.getImageCarById(11L);
        assertNotNull(result);
        assertEquals("monImage1", result.getName());
        assertEquals(23456, result.getSize());
    }

    @Test
    public void createImageCar_shouldCallRepositorySave() {
        imageCarService.createImageCar(testingImageCar);
        Mockito.verify(imageCarRepository).save(testingImageCar);
    }

    @Test
    public void deleteImageCar_shouldReturnTrue_whenExists() {
        Mockito.when(imageCarRepository.existsById(11L)).thenReturn(true);

        Boolean result = imageCarService.deleteImageCar(11L);
        assertTrue(result);
        Mockito.verify(imageCarRepository).deleteById(11L);
    }

    @Test
    public void deleteImageCar_shouldReturnFalse_whenNotExists() {
        Mockito.when(imageCarRepository.existsById(99L)).thenReturn(false);

        Boolean result = imageCarService.deleteImageCar(99L);
        assertFalse(result);
        Mockito.verify(imageCarRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }

    @Test
    public void updateImageCar_shouldReturnUpdatedImageCar_whenExists() {
        ImageCar updatedImageCar = new ImageCar();
        updatedImageCar.setName("monImage2");
        updatedImageCar.setSize(34567);

        Mockito.when(imageCarRepository.findById(11L)).thenReturn(Optional.of(testingImageCar));
        Mockito.when(imageCarRepository.save(Mockito.any(ImageCar.class))).thenAnswer(i ->i.getArgument(0));

        ImageCar result = imageCarService.updateImageCar(11L, updatedImageCar);
        assertNotNull(result);
        assertEquals("monImage2", result.getName());
        assertEquals(34567, result.getSize());
    }

    @Test
    public void updateImageCar_shouldReturnNull_whenNotFound() {
        ImageCar updatedImageCar = new ImageCar();
        updatedImageCar.setName("monImage3");

        Mockito.when(imageCarRepository.findById(99L)).thenReturn(Optional.empty());

        ImageCar result = imageCarService.updateImageCar(99L, updatedImageCar);
        assertNull(result);
    }
}
