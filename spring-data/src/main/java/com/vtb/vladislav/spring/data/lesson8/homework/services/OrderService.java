package com.vtb.vladislav.spring.data.lesson8.homework.services;

import com.vtb.vladislav.spring.data.lesson8.homework.beans.Cart;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.Order;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.OrderItem;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.User;
import com.vtb.vladislav.spring.data.lesson8.homework.exceptions.ResourceNotFoundException;
import com.vtb.vladislav.spring.data.lesson8.homework.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private OrderItemService orderItemService;
    private UserService userService;
    private Cart cart;

    public Order saveOrUpdateOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Заказ с таким id отсутствует в базе данных"));
    }

    @Transactional
    public Order processOrder(String username) {
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь %s не найден", username)));
        Order order = new Order();
        order.setUser(user);
        order.setPrice(cart.getTotalPrice());
        order.setOrderStatus(Order.OrderStatus.PROCESSING);
        order = saveOrUpdateOrder(order);
        for (OrderItem orderItem : cart.getOrderItems()) {
            orderItem.setOrder(order);
            orderItemService.saveOrUpdateOrderItem(orderItem);
        }
        cart.clearCart();
        return order;
    }
}
