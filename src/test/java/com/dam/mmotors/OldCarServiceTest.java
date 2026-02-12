package com.dam.mmotors;

import com.dam.mmotors.pojo.OldCar;
import com.dam.mmotors.repositories.OldCarRepository;
import com.dam.mmotors.services.OldCarService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OldCarServiceTest {

    @Mock
    private OldCarRepository oldCarRepository;

    @InjectMocks
    private OldCarService oldCarService;

        @Test
        public void getOldCarById_shouldReturnCar_whenExists() {
            OldCar testingOldCar =  new OldCar();
            testingOldCar.setOldCar_id(11L);
            testingOldCar.setBrand("Tesla");
            testingOldCar.setPrice(25000);

            Mockito.when(oldCarRepository.findById(11L)).thenReturn(Optional.of(testingOldCar));

            OldCar result = oldCarService.getOldCarById(11L);



            assertNotNull(result);
        }



}
