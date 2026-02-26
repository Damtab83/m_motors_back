package com.dam.mmotors.services;

import com.dam.mmotors.pojo.ImageCar;
import com.dam.mmotors.repositories.ImageCarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ImageCarService {

    @Autowired
    private ImageCarRepository imageCarRepository;

    public List<ImageCar> getAllImageCar() {return imageCarRepository.findAll();}

    public ImageCar getImageCarById(long id) {return imageCarRepository.findById(id).orElse(null);}

    public void createImageCar(ImageCar myImageCar) {imageCarRepository.save(myImageCar);}

    public Boolean deleteImageCar(Long id) {
        Boolean toDelete = imageCarRepository.existsById(id);
        if(toDelete) {
            imageCarRepository.deleteById(id);
        }
        return toDelete;
    }

    public ImageCar updateImageCar(long id, ImageCar newImageCar) {
        ImageCar oldImageCar = this.getImageCarById(id);
        if(oldImageCar != null) {
            oldImageCar.setName(newImageCar.getName());
            oldImageCar.setSize(newImageCar.getSize());
            return imageCarRepository.save(oldImageCar);
        }
        return oldImageCar;
    }
}
