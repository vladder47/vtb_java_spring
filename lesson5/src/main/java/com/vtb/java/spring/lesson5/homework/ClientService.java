package com.vtb.java.spring.lesson5.homework;

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
