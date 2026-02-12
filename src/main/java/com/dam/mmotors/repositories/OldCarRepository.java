package com.dam.mmotors.repositories;

import com.dam.mmotors.pojo.OldCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OldCarRepository extends JpaRepository<OldCar, Long> {
}
