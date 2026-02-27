package com.dam.mmotors.ws;

import com.dam.mmotors.pojo.User;
import com.dam.mmotors.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_USER)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAlluser() {
        List<User> myListUsers = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(myListUsers);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        User myUser = userService.getUserById(id);
        return myUser == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myUser);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User myUser) {
        userService.createUser(myUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        Boolean toDelete = userService.deleteUserById(id);
        return toDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User newUser) {
        userService.updateUser(id, newUser);
        return ResponseEntity.status(HttpStatus.OK).build();}
}
