package com.dam.mmotors.repositories;

import com.dam.mmotors.pojo.BuyCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyCarRepository extends JpaRepository<BuyCar, Long> {
}
