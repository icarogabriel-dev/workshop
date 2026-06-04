package com.icarogabriel.workshop.config;

import com.icarogabriel.workshop.entities.User;
import com.icarogabriel.workshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(null, "Fernando Torres", "fernandot@gmail.com", "27999140746", "fernan789");
        User user2 = new User(null, "Mirella Silva", "mirellaslv@hotmail.com", "11993085127", "silvamirella1");

        userRepository.saveAll(Arrays.asList(user1, user2));
    }
}
