package com.vtb.java.spring.boot.lesson7.homework.services;

import com.vtb.java.spring.boot.lesson7.homework.exceptions.ResourceNotFoundException;
import com.vtb.java.spring.boot.lesson7.homework.models.Client;
import com.vtb.java.spring.boot.lesson7.homework.repositories.ClientRepository;
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

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public void saveOrUpdateClient(Client client) {
        clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Клиент с таким id отсутствует в базе данных"));
    }

    public void removeClientById(Long id) {
        clientRepository.deleteById(id);
    }
}
