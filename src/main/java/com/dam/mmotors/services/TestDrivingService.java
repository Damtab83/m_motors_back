package com.dam.mmotors.services;

import com.dam.mmotors.pojo.TestDriving;
import com.dam.mmotors.repositories.TestDrivingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestDrivingService {

    @Autowired
    private TestDrivingRepository testDrivingRepository;

    public TestDriving getTestDrivingById(long id) {return testDrivingRepository.findById(id).orElse(null);}

    public void createTestDriving(TestDriving myTestDriving) {testDrivingRepository.save(myTestDriving);}

    public Boolean deleteTestDriving(Long id) {
        Boolean toDelete = testDrivingRepository.existsById(id);
        if(toDelete) {
            testDrivingRepository.deleteById(id);
        }
        return toDelete;
    }

    public void updateTestDriving(Long id, TestDriving newTestDriving) {
        TestDriving oldTestDriving = this.getTestDrivingById(id);
        if(oldTestDriving != null) {
            oldTestDriving.setTestDate(newTestDriving.getTestDate());
            oldTestDriving.setConfirmed(newTestDriving.getConfirmed());
            testDrivingRepository.save(oldTestDriving);
        }
    }
}
