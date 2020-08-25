package com.vtb.vladislav.spring.data.lesson8.homework.receivers;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.Order;
import com.vtb.vladislav.spring.data.lesson8.homework.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReadyOrdersReceiver {
    private RabbitTemplate rabbitTemplate;
    private OrderService orderService;

    public void receiveMessage(byte[] message) {
        Long orderId = Long.parseLong(new String(message));
        System.out.println("Заказ №" + orderId.toString() + " готов");
        Order order = orderService.findOrderById(orderId);
        order.setOrderStatus(Order.OrderStatus.READY);
        orderService.saveOrUpdateOrder(order);
    }
}
