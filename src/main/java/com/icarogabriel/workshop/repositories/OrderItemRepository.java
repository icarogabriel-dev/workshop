package com.icarogabriel.workshop.repositories;

import com.icarogabriel.workshop.entities.OrderItem;
import com.icarogabriel.workshop.entities.pk.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> { }
