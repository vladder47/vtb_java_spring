package com.vtb.vladislav.spring.data.lesson8.homework.controllers;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.User;
import com.vtb.vladislav.spring.data.lesson8.homework.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {
    private UserService userService;

    @GetMapping
    public String showProfilePage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile_page";
    }
}
