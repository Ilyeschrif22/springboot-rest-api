package com.demo.controller;

import com.demo.entity.User;
import com.demo.model.UserDTO;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users/add")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("users/get")
    List<User> getUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/users/{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    if (newUser.getUsername() != null) user.setUsername(newUser.getUsername());
                    if (newUser.getPassword() != null) user.setPassword(newUser.getPassword());
                    if (newUser.getEmail() != null) user.setEmail(newUser.getEmail());
                    if (newUser.getAddress() != null) user.setAddress(newUser.getAddress());
                    if (newUser.getPhone() != null) user.setPhone(newUser.getPhone());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }


    @DeleteMapping("/users/{id}")
    void deleteUserById(@PathVariable Long id) {
        userRepository.deleteById(id);
    }





}