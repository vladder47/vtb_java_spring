package com.vtb.vladislav.spring.data.lesson8.homework.controllers;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.Book;
import com.vtb.vladislav.spring.data.lesson8.homework.entities.Genre;
import com.vtb.vladislav.spring.data.lesson8.homework.services.BookService;
import com.vtb.vladislav.spring.data.lesson8.homework.utils.BookFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String showAllBooks(Model model,
                               @RequestParam(name = "page", defaultValue = "1") Integer page,
                               @RequestParam(required = false) MultiValueMap<String, String> params) {
        BookFilter bookFilter = new BookFilter(params);
        Page<Book> pageBook = bookService.findAllBooks(bookFilter.getSpec(), page - 1, 5);
        int totalPages = pageBook.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("requestParameters", bookFilter.getRequestParameters());
        model.addAttribute("page", pageBook);
        model.addAttribute("genres", Genre.values());
        return "all_books";
    }
}
