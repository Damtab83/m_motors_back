package com.dam.mmotors.repositories;

import com.dam.mmotors.pojo.TestDriving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDrivingRepository extends JpaRepository<TestDriving, Long> {
}
