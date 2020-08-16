package com.vtb.vladislav.spring.data.lesson8.homework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String showAdminPage() {
        return "admin_page";
    }
}
