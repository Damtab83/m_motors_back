package com.dam.mmotors.services;

import com.dam.mmotors.pojo.RentalCar;
import com.dam.mmotors.repositories.OldCarRepository;
import com.dam.mmotors.repositories.RentalCarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RentalCarService {

    @Autowired
    private RentalCarRepository rentalCarRepository;

    public List<RentalCar> getAllRentalCar() { return rentalCarRepository.findAll();}

    public RentalCar getRentalCarById(Long id) { return rentalCarRepository.findById(id).orElse(null);}

    public void createRentalCar(RentalCar myRentalCar) { rentalCarRepository.save(myRentalCar);}

    public Boolean deleteRentalCar(Long id) {
        Boolean toDelete = rentalCarRepository.existsById(id);
        if(toDelete) {
            rentalCarRepository.deleteById(id);
        }
        return toDelete;
    }

    public void updateRentalCar(Long id, RentalCar newRentalCar) {
        RentalCar oldRentalCar = this.getRentalCarById(id);
        if(oldRentalCar != null) {
            oldRentalCar.setPrice(newRentalCar.getPrice());
            oldRentalCar.setRentalSubscription(newRentalCar.getRentalSubscription());
            rentalCarRepository.save(oldRentalCar);
        }
    }
}
