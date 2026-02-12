package com.dam.mmotors.services;

import com.dam.mmotors.pojo.BuyCar;
import com.dam.mmotors.repositories.BuyCarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BuyCarService {

    @Autowired
    private BuyCarRepository buyCarRepository;

    public List<BuyCar> getAllBuyCar() { return buyCarRepository.findAll();}

    public BuyCar getBuyCarById(Long id) {return buyCarRepository.findById(id).orElse(null);}

    public void createBuyCar(BuyCar myBuyCar) { buyCarRepository.save(myBuyCar);}

    public Boolean deleteBuyCar(Long id) {
        Boolean toDelete = buyCarRepository.existsById(id);
        if(toDelete) {
            buyCarRepository.deleteById(id);
        }
        return toDelete;
    }

    public void updateBuyCar(Long id, BuyCar newBuyCar) {
        BuyCar oldBuyCar = this.getBuyCarById(id);
        if(oldBuyCar != null) {
            oldBuyCar.setPrice(newBuyCar.getPrice());
            oldBuyCar.setTakeOldCar(newBuyCar.getTakeOldCar());
            buyCarRepository.save(oldBuyCar);
        }
    }
}
