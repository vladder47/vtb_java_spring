package com.vtb.java.spring.mvc.lesson6.homework.services;

import com.vtb.java.spring.mvc.lesson6.homework.models.Client;
import com.vtb.java.spring.mvc.lesson6.homework.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void saveClient(Client client) {
        clientRepository.saveClient(client);
    }

    public void saveClients(List<Client> clients) {
        clientRepository.saveClients(clients);
    }

    public Client getClientById(long id) {
        return clientRepository.getClientById(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }
}
