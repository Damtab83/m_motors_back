package com.dam.mmotors.ws;

import com.dam.mmotors.pojo.TestDriving;
import com.dam.mmotors.services.TestDrivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_TESTDRIVING)
public class TestDrivingController {

    @Autowired
    private TestDrivingService testDrivingService;

    @GetMapping("{id}")
    public ResponseEntity<Object> getTestDrivingById(@PathVariable Long id) {
        TestDriving myTestDriving = testDrivingService.getTestDrivingById(id);
        return myTestDriving == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myTestDriving);
    }

    @PostMapping
    public ResponseEntity<Object> createTestDriving(@RequestBody TestDriving testDriving) {
        testDrivingService.createTestDriving(testDriving);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteTestDriving(@PathVariable Long id) {
        Boolean isDelete = testDrivingService.deleteTestDriving(id);
        return isDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateTestDriving(@PathVariable Long id, @RequestBody TestDriving newTestDriving) {
        testDrivingService.updateTestDriving(id, newTestDriving);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
