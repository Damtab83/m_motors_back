package com.dam.mmotors.ws;

import com.dam.mmotors.pojo.BuyCar;
import com.dam.mmotors.pojo.OldCar;
import com.dam.mmotors.services.BuyCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_BUYCAR)
public class BuyCarController {

    @Autowired
    private BuyCarService buyCarService;

    @GetMapping
    public ResponseEntity<Object> getAllBuyCar() {
        List<BuyCar> myListBuyCars = buyCarService.getAllBuyCar();
        return ResponseEntity.status(HttpStatus.OK).body(myListBuyCars);
    }
    @GetMapping("{id}")
    public ResponseEntity<Object> getBuyCarById(@PathVariable Long id) {
        BuyCar myBuyCar = buyCarService.getBuyCarById(id);
        return myBuyCar == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myBuyCar);
    }

    @PostMapping
    public ResponseEntity<Object> createBuyCar(@RequestBody BuyCar myBuyCar) {
        buyCarService.createBuyCar(myBuyCar);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBuyCarById(@PathVariable Long id) {
        Boolean isDelete = buyCarService.deleteBuyCar(id);
        return isDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateBuyCar(@PathVariable Long id, @RequestBody BuyCar newBuyCar) {
        buyCarService.updateBuyCar(id, newBuyCar);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
