package com.vtb.vladislav.spring.data.lesson8.homework;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.Book;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.Genre;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class BookRestControllerIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    // Т.к. в своей реализации в методе getAllBooks BookRest-контроллера
    // я использовал ResponseEntity<Map<String, Object>>, чтобы передавать в него помимо Page<Book> еще и список категорий,
    // то в соответствующих тестах я использовал restTemplate.exchange и затем делал довольно жуткие преобразования.
    // К сожалению, я не смог найти какой-нибудь другой более действенный способ.
    // Хотел бы узнать можно ли это было сделать как-нибудь более красиво?
    // Или может быть сама реализация контроллера должна выглядеть как-то по иному?

    // Аннотацию Order использовал, чтобы методы по добавлению, изменению и удалению книг срабатывали в последнюю очередь

    @Test
    @Order(1)
    public void getAllBooksTest() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange("/api/v1/books", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        // вот про эти жуткие преобразования я имел в виду :D
        Map<String, Object> body = response.getBody();
        Map book = (Map) body.get("book");
        List booksList = (List) book.get("content");
        Map bookOne = (Map) booksList.get(0);
        Map bookFive = (Map) booksList.get(4);
        List genres = (List) body.get("genres");

        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(12, book.get("totalPages"));
        Assertions.assertEquals(58, book.get("totalElements"));
        Assertions.assertEquals(5, book.get("size"));
        Assertions.assertEquals(0, book.get("number"));
        Assertions.assertEquals("FANTASY", bookOne.get("genre"));
        Assertions.assertEquals("Harry Potter 1", bookOne.get("title"));
        Assertions.assertEquals("FANTASY", bookFive.get("genre"));
        Assertions.assertEquals("Harry Potter 5", bookFive.get("title"));
        Assertions.assertEquals(Arrays.asList("ROMANCE", "Роман"), genres.get(2));
    }

    @Test
    @Order(2)
    public void getAllBooksTestWithMinPrice() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange("/api/v1/books?minPrice=1400", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        Map<String, Object> body = response.getBody();
        Map book = (Map) body.get("book");
        List booksList = (List) book.get("content");
        Map bookOne = (Map) booksList.get(0);
        Map bookThree = (Map) booksList.get(2);
        List genres = (List) body.get("genres");

        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("THRILLER", bookOne.get("genre"));
        Assertions.assertEquals("Dracula 3", bookOne.get("title"));
        Assertions.assertEquals(1400.0, bookOne.get("price"));
        Assertions.assertEquals("THRILLER", bookThree.get("genre"));
        Assertions.assertEquals(1400.0, bookThree.get("price"));
        Assertions.assertEquals(Arrays.asList("THRILLER", "Триллер"), genres.get(3));
    }

    @Test
    @Order(3)
    public void getAllBooksTestWithMaxPrice() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange("/api/v1/books?maxPrice=500", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        Map<String, Object> body = response.getBody();
        Map book = (Map) body.get("book");
        List booksList = (List) book.get("content");
        Map bookOne = (Map) booksList.get(0);
        Map bookThree = (Map) booksList.get(2);
        List genres = (List) body.get("genres");

        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("FANTASY", bookOne.get("genre"));
        Assertions.assertEquals("Harry Potter 1", bookOne.get("title"));
        Assertions.assertEquals("FANTASY", bookThree.get("genre"));
        Assertions.assertEquals(300.0, bookOne.get("price"));
        Assertions.assertEquals("Harry Potter 3", bookThree.get("title"));
        Assertions.assertEquals(500.0, bookThree.get("price"));
    }

    @Test
    @Order(4)
    public void getAllBooksTestWithTitlePart() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange("/api/v1/books?titlePart=Harry", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        Map<String, Object> body = response.getBody();
        Map book = (Map) body.get("book");
        List booksList = (List) book.get("content");
        Map bookOne = (Map) booksList.get(0);
        Map bookThree = (Map) booksList.get(2);

        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("FANTASY", bookOne.get("genre"));
        Assertions.assertEquals("Harry Potter 1", bookOne.get("title"));
        Assertions.assertEquals("FANTASY", bookThree.get("genre"));
        Assertions.assertEquals("Harry Potter 3", bookThree.get("title"));
    }

    @Test
    @Order(5)
    public void getAllBooksTestWithMinPublishYear() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange("/api/v1/books?minPublishYear=2006", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        Map<String, Object> body = response.getBody();
        Map book = (Map) body.get("book");
        List booksList = (List) book.get("content");
        Map bookOne = (Map) booksList.get(0);
        Map bookTwo = (Map) booksList.get(1);

        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("FANTASY", bookOne.get("genre"));
        Assertions.assertEquals("Harry Potter 7", bookOne.get("title"));
        Assertions.assertEquals(2006, bookOne.get("publishYear"));
        Assertions.assertEquals("FANTASY", bookTwo.get("genre"));
        Assertions.assertEquals("LOTR 1", bookTwo.get("title"));
        Assertions.assertEquals(2006, bookTwo.get("publishYear"));
    }

    @Test
    @Order(6)
    public void getAllBooksTestWithMaxPublishYear() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange("/api/v1/books?maxPublishYear=1990", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        Map<String, Object> body = response.getBody();
        Map book = (Map) body.get("book");
        List booksList = (List) book.get("content");
        Map bookOne = (Map) booksList.get(0);
        Map bookTwo = (Map) booksList.get(1);

        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("DETECTIVE", bookOne.get("genre"));
        Assertions.assertEquals("Sherlock Holmes 1", bookOne.get("title"));
        Assertions.assertEquals(1980, bookOne.get("publishYear"));
        Assertions.assertEquals("DETECTIVE", bookTwo.get("genre"));
        Assertions.assertEquals("Sherlock Holmes 2", bookTwo.get("title"));
        Assertions.assertEquals(1987, bookTwo.get("publishYear"));
    }

    @Test
    @Order(7)
    public void getAllBooksTestWithOneGenre() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange("/api/v1/books?genre=ROMANCE", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        Map<String, Object> body = response.getBody();
        Map book = (Map) body.get("book");
        List booksList = (List) book.get("content");
        Map bookOne = (Map) booksList.get(0);
        Map bookThree = (Map) booksList.get(2);

        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("ROMANCE", bookOne.get("genre"));
        Assertions.assertEquals("The Notebook 1", bookOne.get("title"));
        Assertions.assertEquals("ROMANCE", bookThree.get("genre"));
        Assertions.assertEquals("The Notebook 3", bookThree.get("title"));
    }

    @Test
    @Order(8)
    public void getAllBooksTestWithTwoGenre() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange("/api/v1/books?genre=ROMANCE&genre=DETECTIVE",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        Map<String, Object> body = response.getBody();
        Map book = (Map) body.get("book");
        List booksList = (List) book.get("content");
        Map bookOne = (Map) booksList.get(0);
        Map bookFive = (Map) booksList.get(4);

        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("DETECTIVE", bookOne.get("genre"));
        Assertions.assertEquals("Sherlock Holmes 1", bookOne.get("title"));
        Assertions.assertEquals("ROMANCE", bookFive.get("genre"));
        Assertions.assertEquals("The Notebook 2", bookFive.get("title"));
    }

    @Test
    @Order(9)
    public void getAllBooksWithMultipleParams() {
        String url = "/api/v1/books?minPrice=200&maxPrice=400" +
                "&minPublishYear=1980&maxPublishYear=2006&genre=FANTASY&genre=DETECTIVE";
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        Map<String, Object> body = response.getBody();
        Map book = (Map) body.get("book");
        List booksList = (List) book.get("content");
        Map bookOne = (Map) booksList.get(0);
        Map bookFour = (Map) booksList.get(3);

        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("FANTASY", bookOne.get("genre"));
        Assertions.assertEquals("Harry Potter 1", bookOne.get("title"));
        Assertions.assertEquals(300.0, bookOne.get("price"));
        Assertions.assertEquals(2000, bookOne.get("publishYear"));
        Assertions.assertEquals("DETECTIVE", bookFour.get("genre"));
        Assertions.assertEquals("Sherlock Holmes 1", bookFour.get("title"));
        Assertions.assertEquals(400.00, bookFour.get("price"));
        Assertions.assertEquals(1980, bookFour.get("publishYear"));
    }

    @Test
    @Order(10)
    public void getAllBooksWithNotFirstPage() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange("/api/v1/books?page=3", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        Map<String, Object> body = response.getBody();
        Map book = (Map) body.get("book");
        List booksList = (List) book.get("content");
        Map bookOne = (Map) booksList.get(0);
        Map bookFive = (Map) booksList.get(4);

        Assertions.assertSame(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("FANTASY", bookOne.get("genre"));
        Assertions.assertEquals("Hobbit", bookOne.get("title"));
        Assertions.assertEquals("ROMANCE", bookFive.get("genre"));
        Assertions.assertEquals("The Notebook 1", bookFive.get("title"));
        Assertions.assertEquals(2, book.get("number"));
    }

    @Test
    @Order(11)
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
    @Order(12)
    public void getBookByIdTestFail() {
        ResponseEntity<Book> response = restTemplate.getForEntity("/api/v1/books/{id}", Book.class, "60");
        Assertions.assertSame(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Order(13)
    public void createBookTest() {
        Book createdBook = new Book(null, "Test Book", "Test description", Genre.FANTASY, new BigDecimal("500.00"), 2005, null);
        HttpEntity<Book> entity = new HttpEntity<>(createdBook);
        ResponseEntity<Book> response = restTemplate.exchange("/api/v1/books", HttpMethod.POST, entity, Book.class);
        Book book = response.getBody();
        Assertions.assertSame(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(59L, book.getId());
        Assertions.assertEquals("Test Book", book.getTitle());
        Assertions.assertEquals("Test description", book.getDescription());
        Assertions.assertEquals(Genre.FANTASY, book.getGenre());
        Assertions.assertEquals(new BigDecimal("500.00"), book.getPrice());
        Assertions.assertEquals(2005, book.getPublishYear());
        Assertions.assertNull(book.getOrderItems());
    }

    @Test
    @Order(14)
    public void updateBookTest() {
        Book updatedBook = new Book(1L, "Harry Potter 123", "Description Update", Genre.FANTASY, new BigDecimal("500.00"), 2005, null);
        HttpEntity<Book> entity = new HttpEntity<>(updatedBook);
        ResponseEntity<Book> response = restTemplate.exchange("/api/v1/books", HttpMethod.PUT, entity, Book.class);
        Book book = response.getBody();
        Assertions.assertSame(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1L, book.getId());
        Assertions.assertEquals("Harry Potter 123", book.getTitle());
        Assertions.assertEquals("Description Update", book.getDescription());
        Assertions.assertEquals(Genre.FANTASY, book.getGenre());
        Assertions.assertEquals(new BigDecimal("500.00"), book.getPrice());
        Assertions.assertEquals(2005, book.getPublishYear());
        Assertions.assertNull(book.getOrderItems());
    }

    @Test
    @Order(15)
    public void updateBookTestFail() {
        Book updatedBook = new Book(70L, "Harry Potter 123", "Description Update", Genre.FANTASY, new BigDecimal("500.00"), 2005, null);
        HttpEntity<Book> entity = new HttpEntity<>(updatedBook);
        ResponseEntity<Book> response = restTemplate.exchange("/api/v1/books", HttpMethod.PUT, entity, Book.class);
        Assertions.assertSame(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    @Order(16)
    public void deleteBookTest() {
        ResponseEntity<Book> response = restTemplate.exchange("/api/v1/books/{id}", HttpMethod.DELETE, null, Book.class, "1");
        Assertions.assertSame(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(17)
    public void deleteAllBooksTest() {
        ResponseEntity<Book> response = restTemplate.exchange("/api/v1/books", HttpMethod.DELETE, null, Book.class);
        Assertions.assertSame(HttpStatus.OK, response.getStatusCode());
    }
}
