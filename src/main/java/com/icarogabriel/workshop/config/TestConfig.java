package com.icarogabriel.workshop.config;

import com.icarogabriel.workshop.entities.Order;
import com.icarogabriel.workshop.entities.User;
import com.icarogabriel.workshop.repositories.OrderRepository;
import com.icarogabriel.workshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public TestConfig(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User(null, "Fernando Torres", "fernandot@gmail.com", "27999140746", "fernan789");
        User user2 = new User(null, "Mirella Silva", "mirellaslv@hotmail.com", "11993085127", "silvamirella1");
        userRepository.saveAll(List.of(user1, user2));

        Order order1 = new Order(null, Instant.parse("2026-02-12T22:41:05Z"), user2);
        Order order2 = new Order(null, Instant.parse("2026-01-04T01:56:22Z"), user1);
        Order order3 = new Order(null, Instant.parse("2026-06-25T14:06:39Z"), user2);
        orderRepository.saveAll(List.of(order1, order2, order3));
    }
}
