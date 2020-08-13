package com.vtb.vladislav.spring.data.lesson8.homework.repositories;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
