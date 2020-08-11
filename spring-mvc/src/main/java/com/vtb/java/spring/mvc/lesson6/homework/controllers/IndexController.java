package com.vtb.java.spring.mvc.lesson6.homework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String showIndexPage() {
        return "index_page";
    }
}
