package com.example.UserLoginSpring.controller;

import com.example.UserLoginSpring.model.User;
import com.example.UserLoginSpring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    final private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

//        return ResponseEntity.created(URI.create("/api/users/" + createdUser.getId())).body(createdUser);
    }

    @GetMapping("/showAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/showAll/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);

        if (deleted) {
            return "[]";
        }
        return "Usuario não encontrado com id: " + id;
    }
}
