package com.icarogabriel.workshop.controllers;

import com.icarogabriel.workshop.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @GetMapping
    public ResponseEntity<User> findAll() {
        User u = new User(1L, "Fernando", "fernandocampos@gmail.com", "21992924568", "campos021");
        return ResponseEntity.ok().body(u);
    }
}
