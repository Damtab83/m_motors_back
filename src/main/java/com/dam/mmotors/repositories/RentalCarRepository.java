package com.dam.mmotors.repositories;

import com.dam.mmotors.pojo.RentalCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalCarRepository extends JpaRepository<RentalCar, Long> {
}
