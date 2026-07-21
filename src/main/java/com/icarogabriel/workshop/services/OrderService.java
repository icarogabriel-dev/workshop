package com.icarogabriel.workshop.services;

import com.icarogabriel.workshop.entities.Order;
import com.icarogabriel.workshop.entities.OrderItem;
import com.icarogabriel.workshop.entities.enums.OrderStatus;
import com.icarogabriel.workshop.repositories.OrderItemRepository;
import com.icarogabriel.workshop.repositories.OrderRepository;
import com.icarogabriel.workshop.repositories.ProductRepository;
import com.icarogabriel.workshop.repositories.UserRepository;
import com.icarogabriel.workshop.services.exceptions.DatabaseException;
import com.icarogabriel.workshop.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> obj = orderRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Order insert(Order obj) {

        obj.setId(null);
        obj.setMoment(Instant.now());
        obj.setOrderStatus(OrderStatus.WAITING_PAYMENT);

        obj.setClient(userRepository.getReferenceById(obj.getClient().getId()));

        for (OrderItem item : obj.getItems()) {
            item.setOrder(obj);
            item.setProduct(productRepository.getReferenceById(item.getProduct().getId()));
            item.setPrice(item.getProduct().getPrice());
        }

        obj = orderRepository.save(obj);
        orderItemRepository.saveAll(obj.getItems());
        return obj;
    }

    public void delete(Long id) {

        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }

        try {
            orderRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Order update(Long id, Order obj) {

        try {
            Order entity = orderRepository.getReferenceById(id);
            updateData(entity, obj);
            return orderRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Order entity, Order obj) {
        entity.setOrderStatus(obj.getOrderStatus());
    }
}
