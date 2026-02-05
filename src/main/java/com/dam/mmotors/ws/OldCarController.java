package com.dam.mmotors.ws;

import com.dam.mmotors.pojo.OldCar;
import com.dam.mmotors.services.OldCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_OLDCAR)
public class OldCarController {

    @Autowired
    private OldCarService oldCarService;

    @GetMapping
    public ResponseEntity<Object> getAllOldCars () {
        List<OldCar> myListOldCar = oldCarService.getAllOldCars();
        return ResponseEntity.status(HttpStatus.OK).body(myListOldCar);
    }
    @GetMapping("{id}")
    public ResponseEntity<OldCar> getOldCarById(Long id) {
        OldCar myOldCar = oldCarService.getOldCarById(id);
        return myOldCar == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myOldCar);
    }

    @PostMapping
    public ResponseEntity<Object> createOldCar(@RequestBody OldCar myOldCar) {
        oldCarService.createOldCar(myOldCar);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteOldCarById(@PathVariable Long id) {
        Boolean isDelete = oldCarService.deleteOldCarById(id);
        return isDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateOldCar(@PathVariable Long id, @RequestBody OldCar newOldCar) {
        oldCarService.updateOldCar(id, newOldCar);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
