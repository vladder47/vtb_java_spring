package com.vtb.vladislav.spring.data.lesson8.homework.controllers;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.Order;
import com.vtb.vladislav.spring.data.lesson8.homework.receivers.ReadyOrdersReceiver;
import com.vtb.vladislav.spring.data.lesson8.homework.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private static final String READY_ORDERS_QUEUE = "readyOrdersQueue";
    private static final String EXCHANGE_FOR_PROCESSING_ORDERS = "processingOrdersExchanger";

    private OrderService orderService;
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/process")
    public String processOrder(Model model, Principal principal) {
        Order order = orderService.processOrder(principal.getName());
        rabbitTemplate.convertAndSend(EXCHANGE_FOR_PROCESSING_ORDERS, null, order.getId().toString());
        model.addAttribute("order", order);
        return "order_page";
    }

    @Bean
    public SimpleMessageListenerContainer containerForReadyOrdersQueue(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(READY_ORDERS_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(ReadyOrdersReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
