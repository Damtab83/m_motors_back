package com.dam.mmotors.repositories;

import com.dam.mmotors.pojo.ImageCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageCarRepository extends JpaRepository<ImageCar, Long> {
}
