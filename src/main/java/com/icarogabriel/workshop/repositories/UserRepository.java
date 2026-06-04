package com.icarogabriel.workshop.repositories;

import com.icarogabriel.workshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { }
