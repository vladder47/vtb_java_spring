package com.vtb.vladislav.spring.data.lesson8.homework;

import com.vtb.vladislav.spring.data.lesson8.homework.beans.Cart;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.Book;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.Genre;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.OrderItem;
import com.vtb.vladislav.spring.data.lesson8.homework.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootTest
public class CartTests {
    @Autowired
    private Cart cart;

    @MockBean
    private BookService bookService;

    private Book bookOne = new Book(1L, "Harry Potter", "Description One", Genre.FANTASY, new BigDecimal("400.00"), 2005, null);
    private Book bookTwo = new Book(2L, "Sherlock Holmes", "Description Two", Genre.DETECTIVE, new BigDecimal("500.00"), 1997, null);

    @BeforeEach
    public void setUp() {
        Mockito.doReturn(bookOne)
                .when(bookService)
                .findBookById(bookOne.getId());

        Mockito.doReturn(bookTwo)
                .when(bookService)
                .findBookById(bookTwo.getId());
    }

    @Test
    public void addBookToCartTest() {
        Assertions.assertAll(
                () -> {
                    cart.addBookToCart(1L);
                    OrderItem orderItemOne = cart.getOrderItems().get(0);

                    Assertions.assertEquals(bookOne, orderItemOne.getBook());
                    Assertions.assertEquals(1, orderItemOne.getCount());
                    Assertions.assertEquals(bookOne.getPrice(), orderItemOne.getPrice());
                    Assertions.assertEquals(bookOne.getPrice(), cart.getTotalPrice());
                }, () -> {
                    cart.addBookToCart(2L);
                    BigDecimal totalPrice = bookOne.getPrice().add(bookTwo.getPrice());
                    OrderItem orderItemOne = cart.getOrderItems().get(0);
                    OrderItem orderItemTwo = cart.getOrderItems().get(1);

                    Assertions.assertEquals(bookOne, orderItemOne.getBook());
                    Assertions.assertEquals(bookTwo, orderItemTwo.getBook());
                    Assertions.assertEquals(1, orderItemTwo.getCount());
                    Assertions.assertEquals(bookTwo.getPrice(), orderItemTwo.getPrice());
                    Assertions.assertEquals(totalPrice, cart.getTotalPrice());
                }, () -> {
                    cart.addBookToCart(1L);
                    BigDecimal totalPrice = bookOne.getPrice().multiply(new BigDecimal("2")).add(bookTwo.getPrice());
                    BigDecimal orderItemPrice = bookOne.getPrice().multiply(new BigDecimal("2"));
                    OrderItem orderItemOne = cart.getOrderItems().get(0);
                    OrderItem orderItemTwo = cart.getOrderItems().get(1);

                    Assertions.assertEquals(bookOne, orderItemOne.getBook());
                    Assertions.assertEquals(bookTwo, orderItemTwo.getBook());
                    Assertions.assertEquals(2, orderItemOne.getCount());
                    Assertions.assertEquals(orderItemPrice, orderItemOne.getPrice());
                    Assertions.assertEquals(totalPrice, cart.getTotalPrice());
                }
        );
    }

    @Test
    public void deleteBookFromCartTest() {
        cart.addBookToCart(1L);
        cart.addBookToCart(1L);
        cart.addBookToCart(2L);

        Assertions.assertAll(
                () -> {
                    cart.deleteBookFromCart(1);
                    OrderItem orderItem = cart.getOrderItems().get(0);
                    BigDecimal totalPrice = bookOne.getPrice().add(bookTwo.getPrice());
                    Assertions.assertEquals(bookOne, orderItem.getBook());
                    Assertions.assertEquals(1, orderItem.getCount());
                    Assertions.assertEquals(bookOne.getPrice(), orderItem.getPrice());
                    Assertions.assertEquals(totalPrice, cart.getTotalPrice());
                }, () -> {
                    cart.deleteBookFromCart(1);
                    OrderItem orderItem = cart.getOrderItems().get(0);
                    BigDecimal totalPrice = bookTwo.getPrice();
                    Assertions.assertEquals(bookTwo, orderItem.getBook());
                    Assertions.assertEquals(1, orderItem.getCount());
                    Assertions.assertEquals(bookTwo.getPrice(), orderItem.getPrice());
                    Assertions.assertEquals(totalPrice, cart.getTotalPrice());
                }, () -> {
                    cart.deleteBookFromCart(1);
                    Assertions.assertIterableEquals(Collections.emptyList(), cart.getOrderItems());
                    Assertions.assertEquals(new BigDecimal("0.00"), cart.getTotalPrice());
                }
        );
    }

    @Test
    public void clearCartTest() {
        cart.addBookToCart(1L);
        cart.addBookToCart(1L);
        cart.addBookToCart(2L);
        cart.addBookToCart(2L);

        cart.clearCart();
        Assertions.assertIterableEquals(Collections.emptyList(), cart.getOrderItems());
        Assertions.assertEquals(new BigDecimal("0.00"), cart.getTotalPrice());
    }
}
