package com.dam.mmotors.ws;

import com.dam.mmotors.pojo.Car;
import com.dam.mmotors.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_CAR)
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<Object> getAllCars() {
        List<Car> myListCars = carService.getAllCar();
        return myListCars == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myListCars);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getCarById(@PathVariable Long id) {
        Car myCar = carService.getCarById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<Object> createCar(@RequestBody Car myCar) {
        carService.createCar(myCar);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCarById(@PathVariable Long id) {
        Boolean isDelete = carService.deleteCar(id);
        return isDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateCar(@PathVariable Long id, @RequestBody Car newCar) {
        carService.updateCar(id, newCar);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
