package com.vtb.vladislav.spring.data.lesson8.homework.services;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.OrderItem;
import com.vtb.vladislav.spring.data.lesson8.homework.exceptions.ResourceNotFoundException;
import com.vtb.vladislav.spring.data.lesson8.homework.repositories.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public void saveOrUpdateOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public OrderItem findOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("OrderItem с таким id отсутствует в базе данных"));
    }
}
