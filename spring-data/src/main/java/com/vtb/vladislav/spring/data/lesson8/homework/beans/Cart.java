package com.vtb.vladislav.spring.data.lesson8.homework.beans;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.Book;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.OrderItem;
import com.vtb.vladislav.spring.data.lesson8.homework.services.BookService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class Cart {
    private List<OrderItem> orderItems;
    private BigDecimal totalPrice;
    private BookService bookService;

    @Autowired
    public Cart(BookService bookService) {
        this.bookService = bookService;
        this.orderItems = new ArrayList<>();
        this.totalPrice = new BigDecimal("0.0");
    }

    public void addBookToCart(Long id) {
        Book book = bookService.findBookById(id);
        OrderItem orderItem = searchOrderItemByBook(book);
        if (orderItem != null) {
            orderItem.setCount(orderItem.getCount() + 1);
            orderItem.setPrice(orderItem.getPrice().add(book.getPrice()));
        } else {
            orderItem = new OrderItem(1L, book.getPrice(), book);
            orderItems.add(orderItem);
        }
        totalPrice = totalPrice.add(book.getPrice());
    }

    private OrderItem searchOrderItemByBook(Book book) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getBook().equals(book)) {
                return orderItem;
            }
        }
        return null;
    }

    public void deleteBookFromCart(int index) {
        OrderItem orderItem = orderItems.get(index - 1);
        Book book = orderItem.getBook();
        if (orderItem.getCount() > 1) {
            orderItem.setCount(orderItem.getCount() - 1);
            orderItem.setPrice(orderItem.getPrice().subtract(book.getPrice()));
        } else {
            orderItems.remove(orderItem);
        }
        totalPrice = totalPrice.subtract(orderItem.getBook().getPrice());
    }

    public void clearCart() {
        orderItems.clear();
        totalPrice = new BigDecimal("0.00");
    }
}
