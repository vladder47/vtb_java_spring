package com.vtb.vladislav.spring.data.lesson8.homework.repositories;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
