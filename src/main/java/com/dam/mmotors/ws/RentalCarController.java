package com.dam.mmotors.ws;

import com.dam.mmotors.pojo.OldCar;
import com.dam.mmotors.pojo.RentalCar;
import com.dam.mmotors.services.RentalCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_RENTALCAR)
public class RentalCarController {

    @Autowired
    private RentalCarService rentalCarService;

    @GetMapping
    public ResponseEntity<Object> getAllRentalCar() {
        List<RentalCar> myListRentalCar = rentalCarService.getAllRentalCar();
        return ResponseEntity.status(HttpStatus.OK).body(myListRentalCar);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getRentalCarById(@PathVariable Long id) {
        RentalCar myRentalCar = rentalCarService.getRentalCarById(id);
        return myRentalCar == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myRentalCar);
    }

    @PostMapping
    public ResponseEntity<Object> createRentalCar(@RequestBody RentalCar myRentalCar) {
        rentalCarService.createRentalCar(myRentalCar);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteRentalCar(@PathVariable Long id) {
        Boolean isDelete = rentalCarService.deleteRentalCar(id);
        return isDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateRentalCar(@PathVariable Long id, @RequestBody RentalCar newRentalCar) {
        rentalCarService.updateRentalCar(id, newRentalCar);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
