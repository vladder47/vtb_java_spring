package com.vtb.vladislav.spring.data.lesson8.homework.services;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.Order;
import com.vtb.vladislav.spring.data.lesson8.homework.exceptions.ResourceNotFoundException;
import com.vtb.vladislav.spring.data.lesson8.homework.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;

    public Order saveOrUpdateOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Заказ с таким id отсутствует в базе данных"));
    }
}
