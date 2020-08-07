package com.vtb.java.spring.boot.lesson7.homework.controllers;

import com.vtb.java.spring.boot.lesson7.homework.models.Client;
import com.vtb.java.spring.boot.lesson7.homework.services.ClientService;
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
        clientService.saveOrUpdateClient(client);
        return "redirect:/clients/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteClientById(@PathVariable Long id) {
        clientService.removeClientById(id);
        return "redirect:/clients/all";
    }

    @GetMapping("/edit/{id}")
    public String showEditClientForm(@PathVariable Long id, Model model) {
        model.addAttribute("frontClient", clientService.getClientById(id));
        return "edit_client_form";
    }

    @PostMapping("/edit")
    public String updateClient(@ModelAttribute Client modifiedClient) {
        clientService.saveOrUpdateClient(modifiedClient);
        return "redirect:/clients/all";
    }
}
