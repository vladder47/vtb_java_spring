package com.vtb.vladislav.spring.data.lesson8.homework;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.Book;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookRestControllerIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    // НЕ УСПЕЛ ДОДЕЛАТЬ ЭТИ ТЕСТЫ
    @Test
    public void getAllBooksTest() throws Exception {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange("/api/v1/books", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
        });
//        Map<String, Object> body = response.getBody();
//        Map map = (Map) body.get("book");
//        System.out.println(map.get("content"));
//        for (Object entry : map.entrySet()) {
//            System.out.println(entry);
//        }
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getAllBooksTestWithMinPrice() throws Exception {
        ResponseEntity<Map> response = restTemplate.getForEntity("/api/v1/books?minPrice=300", Map.class);
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getAllBooksTestWithMaxPrice() throws Exception {
        ResponseEntity<Map> response = restTemplate.getForEntity("/api/v1/books?maxPrice=500", Map.class);
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getAllBooksTestWithTitlePart() throws Exception {
        ResponseEntity<Map> response = restTemplate.getForEntity("/api/v1/books?titlePart=Harry", Map.class);
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getAllBooksTestWithMinPublishYear() throws Exception {
        ResponseEntity<Map> response = restTemplate.getForEntity("/api/v1/books?minPublishYear=2007", Map.class);
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getAllBooksTestWithMaxPublishYear() throws Exception {
        ResponseEntity<Map> response = restTemplate.getForEntity("/api/v1/books?maxPublishYear=1995", Map.class);
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getAllBooksTestWithOneGenre() throws Exception {
        ResponseEntity<Map> response = restTemplate.getForEntity("/api/v1/books?genre=FANTASY", Map.class);
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getAllBooksTestWithTwoGenre() throws Exception {
        ResponseEntity<Map> response = restTemplate.getForEntity("/api/v1/books?genre=FANTASY&genre=DETECTIVE", Map.class);
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
    }
    // НЕ УСПЕЛ ДОДЕЛАТЬ ЭТИ ТЕСТЫ

    // ЭТИ ТЕСТЫ ГОТОВЫ
    @Test
    public void getBookByIdTest() {
        ResponseEntity<Book> response = restTemplate.getForEntity("/api/v1/books/{id}", Book.class, "1");
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Book book = response.getBody();
        Assertions.assertEquals(1L, book.getId());
        Assertions.assertEquals("Harry Potter 1", book.getTitle());
        Assertions.assertEquals("Description 1", book.getDescription());
        Assertions.assertEquals(Genre.FANTASY, book.getGenre());
        Assertions.assertEquals(new BigDecimal("300.00"), book.getPrice());
        Assertions.assertEquals(2000, book.getPublishYear());
        Assertions.assertEquals(Collections.emptyList(), book.getOrderItems());
    }

    @Test
    public void getBookByIdTestFail() {
        ResponseEntity<Book> response = restTemplate.getForEntity("/api/v1/books/{id}", Book.class, "60");
        Assertions.assertSame(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void createBookTest() {
        Book createdBook = new Book(null, "Test Book", "Test description", Genre.FANTASY, new BigDecimal("500.00"), 2005, null);
        HttpEntity<Book> entity = new HttpEntity<>(createdBook);
        ResponseEntity<Book> response = restTemplate.exchange("/api/v1/books", HttpMethod.POST, entity, Book.class);
        Book book = response.getBody();
        Assertions.assertSame(response.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(59L, book.getId());
        Assertions.assertEquals("Test Book", book.getTitle());
        Assertions.assertEquals("Test description", book.getDescription());
        Assertions.assertEquals(Genre.FANTASY, book.getGenre());
        Assertions.assertEquals(new BigDecimal("500.00"), book.getPrice());
        Assertions.assertEquals(2005, book.getPublishYear());
        Assertions.assertNull(book.getOrderItems());
    }

    @Test
    public void updateBookTest() {
        Book updatedBook = new Book(1L, "Harry Potter 123", "Description Update", Genre.FANTASY, new BigDecimal("500.00"), 2005, null);
        HttpEntity<Book> entity = new HttpEntity<>(updatedBook);
        ResponseEntity<Book> response = restTemplate.exchange("/api/v1/books", HttpMethod.PUT, entity, Book.class);
        Book book = response.getBody();
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(1L, book.getId());
        Assertions.assertEquals("Harry Potter 123", book.getTitle());
        Assertions.assertEquals("Description Update", book.getDescription());
        Assertions.assertEquals(Genre.FANTASY, book.getGenre());
        Assertions.assertEquals(new BigDecimal("500.00"), book.getPrice());
        Assertions.assertEquals(2005, book.getPublishYear());
        Assertions.assertNull(book.getOrderItems());
    }

    @Test
    public void updateBookTestFail() {
        Book updatedBook = new Book(70L, "Harry Potter 123", "Description Update", Genre.FANTASY, new BigDecimal("500.00"), 2005, null);
        HttpEntity<Book> entity = new HttpEntity<>(updatedBook);
        ResponseEntity<Book> response = restTemplate.exchange("/api/v1/books", HttpMethod.PUT, entity, Book.class);
        Assertions.assertSame(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Test
    public void deleteBookTest() {
        ResponseEntity<Book> response = restTemplate.exchange("/api/v1/books/{id}", HttpMethod.DELETE, null, Book.class, "1");
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deleteAllBooksTest() {
        ResponseEntity<Book> response = restTemplate.exchange("/api/v1/books", HttpMethod.DELETE, null, Book.class);
        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
    }

}
