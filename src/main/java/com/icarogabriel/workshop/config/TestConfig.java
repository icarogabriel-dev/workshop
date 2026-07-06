package com.icarogabriel.workshop.config;

import com.icarogabriel.workshop.entities.*;
import com.icarogabriel.workshop.entities.enums.OrderStatus;
import com.icarogabriel.workshop.repositories.*;
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

    @Autowired
    private OrderItemRepository orderItemRepository;

    public TestConfig(UserRepository userRepository, OrderRepository orderRepository, CategoryRepository categoryRepository,
    ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User(null, "Fernando Torres", "fernandot@gmail.com", "27999140746", "fernan789");
        User user2 = new User(null, "Mirella Silva", "mirellaslv@hotmail.com", "11993085127", "silvamirella1");
        userRepository.saveAll(List.of(user1, user2));

        Order order1 = new Order(null, Instant.parse("2026-02-12T22:41:05Z"), OrderStatus.PAID, user2);
        Order order2 = new Order(null, Instant.parse("2026-01-04T01:56:22Z"), OrderStatus.PAID, user1);
        Order order3 = new Order(null, Instant.parse("2026-06-25T14:06:39Z"), OrderStatus.WAITING_PAYMENT, user2);
        orderRepository.saveAll(List.of(order1, order2, order3));

        Payment payment1 = new Payment(null, Instant.parse("2026-02-12T23:41:05Z"), order1);
        Payment payment2 = new Payment(null, Instant.parse("2026-01-04T02:34:22Z"), order2);
        order1.setPayment(payment1);
        order2.setPayment(payment2);
        orderRepository.saveAll(List.of(order1, order2));

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

        OrderItem orderItem1 = new OrderItem(order1, product1, 2, product1.getPrice());
        OrderItem orderItem2 = new OrderItem(order1, product3, 1, product3.getPrice());
        OrderItem orderItem3 = new OrderItem(order2, product4, 4, product4.getPrice());
        OrderItem orderItem4 = new OrderItem(order3, product5, 3, product5.getPrice());
        OrderItem orderItem5 = new OrderItem(order2, product2, 7, product2.getPrice());
        orderItemRepository.saveAll(List.of(orderItem1, orderItem2, orderItem3, orderItem4, orderItem5));
    }
}
