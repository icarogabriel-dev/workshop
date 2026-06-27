package com.icarogabriel.workshop.config;

import com.icarogabriel.workshop.entities.Category;
import com.icarogabriel.workshop.entities.Order;
import com.icarogabriel.workshop.entities.Product;
import com.icarogabriel.workshop.entities.User;
import com.icarogabriel.workshop.entities.enums.OrderStatus;
import com.icarogabriel.workshop.repositories.CategoryRepository;
import com.icarogabriel.workshop.repositories.OrderRepository;
import com.icarogabriel.workshop.repositories.ProductRepository;
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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public TestConfig(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User(null, "Fernando Torres", "fernandot@gmail.com", "27999140746", "fernan789");
        User user2 = new User(null, "Mirella Silva", "mirellaslv@hotmail.com", "11993085127", "silvamirella1");
        userRepository.saveAll(List.of(user1, user2));

        Order order1 = new Order(null, Instant.parse("2026-02-12T22:41:05Z"), OrderStatus.SHIPPED, user2);
        Order order2 = new Order(null, Instant.parse("2026-01-04T01:56:22Z"), OrderStatus.PAID, user1);
        Order order3 = new Order(null, Instant.parse("2026-06-25T14:06:39Z"), OrderStatus.WAITING_PAYMENT, user2);
        orderRepository.saveAll(List.of(order1, order2, order3));

        Category category1 = new Category(null, "Electronics");
        Category category2 = new Category(null, "Books");
        Category category3 = new Category(null, "Fashion");
        Category category4 = new Category(null, "Food");
        Category category5 = new Category(null, "Object");
        categoryRepository.saveAll(List.of(category1, category2, category3, category4, category5));

        Product product1 = new Product(null, "PC Gamer", "Is a high-performance computer designed to run heavy games with fluidity and superior graphics quality.", 5249.99, "");
        Product product2 = new Product(null, "Rice", "Is a cereal from the grass family, being the food base of more than half of the world's population.", 10.99, "");
        Product product3 = new Product(null, "Smart TV", "It is a television with integrated internet that allows you to download applications and browse the web.", 3199.99, "");
        Product product4 = new Product(null, "Iphone X", "Is Apple's line of high-end smartphones, operating exclusively with the iOS system.", 1559.99, "");
        Product product5 = new Product(null, "Mirror", "Is a smooth, polished surface, generally composed of flat or curved glass and a metallic layer on the back.", 79.99, "");
        productRepository.saveAll(List.of(product1, product2, product3, product4, product5));

        product1.getCategories().add(category1);
        product2.getCategories().add(category4);
        product3.getCategories().add(category1);
        product4.getCategories().add(category1);
        product5.getCategories().add(category5);
        productRepository.saveAll(List.of(product1, product2, product3, product4, product5));
    }
}
