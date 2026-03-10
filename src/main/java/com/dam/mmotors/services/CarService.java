package com.dam.mmotors.services;

import com.dam.mmotors.pojo.Car;
import com.dam.mmotors.repositories.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {return carRepository.findAll();}

    public Car getCarById(Long id) {return carRepository.findById(id).orElse(null);}

    public void createCar(Car myCar) {carRepository.save(myCar);}

    public Boolean deleteCar(Long id) {
        Boolean toDelete = carRepository.existsById(id);
        if(toDelete) {
            carRepository.deleteById(id);
        }
        return toDelete;
    }

    public void updateCar(Long id, Car newCar) {
        Car oldCar = this.getCarById(id);
        if(oldCar != null) {
            oldCar.setBrand(newCar.getBrand());
            oldCar.setModel(newCar.getModel());
            oldCar.setImages(newCar.getImages());
            oldCar.setMotorization(newCar.getMotorization());
            oldCar.setKilometer(newCar.getKilometer());
            oldCar.setTesting(newCar.getTesting());
//            oldCar.setBuy(newCar.getBuy());
//            oldCar.setRental(newCar.getRental());
            oldCar.setFunding(newCar.getFunding());
            oldCar.setPrice(newCar.getPrice());
        }
    }
}
