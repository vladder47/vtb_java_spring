package com.vtb.java.spring.mvc.lesson6.homework.controllers;

import com.vtb.java.spring.mvc.lesson6.homework.models.Client;
import com.vtb.java.spring.mvc.lesson6.homework.models.Product;
import com.vtb.java.spring.mvc.lesson6.homework.services.ClientService;
import com.vtb.java.spring.mvc.lesson6.homework.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public String getAllClients(Model model) {
        model.addAttribute("frontClients", clientService.getAllClients());
        return "all_clients";
    }

    @GetMapping("/show_client/{id}")
    public String getClientById(@PathVariable Long id, Model model) {
        model.addAttribute("frontClient", clientService.getClientById(id));
        return "client_page";
    }

    @GetMapping("/add")
    public String showClientAddForm() {
        return "add_client_form";
    }

    @PostMapping("/add")
    public String addNewClient(@ModelAttribute Client client) {
        clientService.saveClient(client);
        return "redirect:/clients/all";
    }
}
