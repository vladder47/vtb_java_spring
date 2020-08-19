package com.vtb.vladislav.spring.data.lesson8.homework.controllers;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.Order;
import com.vtb.vladislav.spring.data.lesson8.homework.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @GetMapping("/process")
    public String processOrder(Model model, Principal principal) {
        Order order = orderService.processOrder(principal.getName());
        model.addAttribute("order", order);
        return "order_page";
    }
}
