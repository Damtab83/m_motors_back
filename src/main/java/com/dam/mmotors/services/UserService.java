package com.dam.mmotors.services;

import com.dam.mmotors.pojo.User;
import com.dam.mmotors.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() { return userRepository.findAll();}

    public User getUserById(Long id) { return userRepository.findById(id).orElse(null);}

    public void createUser(User myUser) { userRepository.save(myUser);}

    public Boolean deleteUserById(Long id) {
        Boolean toDelete = userRepository.existsById(id);
        if(toDelete) {
            userRepository.deleteById(id);
        }
        return toDelete;
    }

    public User updateUser(Long id, User newUser) {
        User oldUser = this.getUserById(id);
        if (oldUser == null) {
            throw new RuntimeException("User not found");
        }
        if(oldUser != null) {
            oldUser.setEmail(newUser.getEmail());
            oldUser.setPassword(newUser.getPassword());
            return userRepository.save(oldUser);
        }
        return oldUser;
    }
}
