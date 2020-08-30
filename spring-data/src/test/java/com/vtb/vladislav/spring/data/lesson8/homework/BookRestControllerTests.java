package com.vtb.vladislav.spring.data.lesson8.homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtb.vladislav.spring.data.lesson8.homework.controllers.rest.BookRestController;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.Book;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.Genre;
import com.vtb.vladislav.spring.data.lesson8.homework.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(BookRestController.class)
public class BookRestControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private Book bookOne = new Book(1L, "Harry Potter", "Description One", Genre.FANTASY, new BigDecimal("400.00"), 2005, null);
    private Book bookTwo = new Book(2L, "Sherlock Holmes", "Description Two", Genre.DETECTIVE, new BigDecimal("500.00"), 1997, null);
    private Book bookThree = new Book(3L, "Dracula", "Description Three", Genre.THRILLER, new BigDecimal("700.00"), 1990, null);

    @BeforeEach
    public void setUp() {
        List<Book> books = Arrays.asList(bookOne, bookTwo, bookThree);
        Mockito.doReturn(books)
                .when(bookService)
                .findAllBooks();

        Mockito.doReturn(bookOne)
                .when(bookService)
                .findBookById(bookOne.getId());

        Mockito.doReturn(bookOne)
                .when(bookService)
                .saveOrUpdate(bookOne);

        Mockito.doReturn(true)
                .when(bookService)
                .existsById(bookOne.getId());

        Mockito.doNothing()
                .when(bookService)
                .deleteById(3L);

        Mockito.doNothing()
                .when(bookService)
                .deleteAll();
    }

    // для этого теста отдельно написал метод в контроллере, который возвращает не ResponseEntity,
    // а List<Book> (метод с ResponseEntity тестируется в интеграционном тесте)
    @Test
    public void getAllBooksTest() throws Exception {
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Harry Potter")))
                .andExpect(jsonPath("$[0].description", is("Description One")))
                .andExpect(jsonPath("$[0].genre", is("FANTASY")))
                .andExpect(jsonPath("$[0].price", is(400.00)))
                .andExpect(jsonPath("$[0].publishYear", is(2005)))
                .andExpect(jsonPath("$[0].orderItems", is(nullValue())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Sherlock Holmes")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].title", is("Dracula")));
        verify(bookService, times(1)).findAllBooks();

    }

    @Test
    public void getBookByIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/books/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Harry Potter")))
                .andExpect(jsonPath("$.description", is("Description One")))
                .andExpect(jsonPath("$.genre", is("FANTASY")))
                .andExpect(jsonPath("$.price", is(400.00)))
                .andExpect(jsonPath("$.publishYear", is(2005)))
                .andExpect(jsonPath("$.orderItems", is(nullValue())));
        verify(bookService, times(1)).findBookById(1L);
    }

    // не смог подключить JacksonTester для этого теста, т.к. ругалось на то, что нет соответствующего бина
    @Test
    public void createNewBookTest() throws Exception {
        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookOne)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateBookTest() throws Exception {
        mockMvc.perform(put("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookOne)))
                .andExpect(status().isOk());
        verify(bookService, times(1)).saveOrUpdate(bookOne);
        verify(bookService, times(1)).existsById(bookOne.getId());
    }

    @Test
    public void deleteBookTest() throws Exception {
        mockMvc.perform(delete("/api/v1/books/{id}", bookThree.getId()))
                .andExpect(status().isOk());
        verify(bookService, times(1)).deleteById(bookThree.getId());
    }

    @Test
    public void deleteAllBooksTest() throws Exception {
        mockMvc.perform(delete("/api/v1/books"))
                .andExpect(status().isOk());
        verify(bookService, times(1)).deleteAll();
    }
}
