package com.dam.mmotors.ws;

import com.dam.mmotors.pojo.ImageCar;
import com.dam.mmotors.services.ImageCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_IMAGECAR)
public class ImageCarController {

    @Autowired
    private ImageCarService imageCarService;

    @GetMapping
    public ResponseEntity<Object> getAllImageCar() {
        List<ImageCar> myListImageCar = imageCarService.getAllImageCar();
        return ResponseEntity.status(HttpStatus.OK).body(myListImageCar);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getImageCarById(@PathVariable Long id) {
        ImageCar myImageCar = imageCarService.getImageCarById(id);
        return myImageCar == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myImageCar);
    }

    @PostMapping
    public ResponseEntity<Object> createImageCar(@RequestBody ImageCar myImageCar) {
        imageCarService.createImageCar(myImageCar);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteImageCar(@PathVariable Long id) {
        Boolean toDelete = imageCarService.deleteImageCar(id);
        return toDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateImageCar(@PathVariable Long id, @RequestBody ImageCar newImageCar) {
        imageCarService.updateImageCar(id, newImageCar);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
