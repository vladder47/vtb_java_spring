package com.vtb.vladislav.spring.data.lesson8.homework.controllers;

import com.vtb.vladislav.spring.data.lesson8.homework.beans.Cart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private Cart cart;

    @GetMapping
    public String showCartPage(Model model) {
        model.addAttribute("orderItems", cart.getOrderItems());
        model.addAttribute("totalPrice", cart.getTotalPrice());
        return "cart_page";
    }

    @GetMapping("/{id}")
    public void addBookToCart(Model model, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.addBookToCart(id);
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/delete/{index}")
    public String deleteBookFromCart(@PathVariable int index) {
        cart.deleteBookFromCart(index);
        return "redirect:/cart";
    }

    @GetMapping("/delete")
    public String clearCart() {
        cart.clearCart();
        return "redirect:/cart";
    }
}
