package com.vtb.java.spring.mvc.lesson6.homework.repositories;

import com.vtb.java.spring.mvc.lesson6.homework.models.Client;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vtb.java.spring.mvc.lesson6.homework.HibernateSessionFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class ClientRepository {
    private  final HibernateSessionFactory hsf;

    @Autowired
    public ClientRepository(HibernateSessionFactory hsf) {
        this.hsf = hsf;
    }

    public void saveClient(Client client) {
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
        }
    }

    public void saveClients(List<Client> clients) {
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            for (Client client : clients) {
                session.save(client);
            }
            session.getTransaction().commit();
        }
    }

    public Client getClientById(long id) {
        Client client;
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            client = session.get(Client.class, id);
            session.getTransaction().commit();
        }
        return client;
    }

    public List<Client> getAllClients() {
        List<Client> clients;
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            TypedQuery<Client> query = session.createQuery("SELECT c FROM Client c", Client.class);
            clients = query.getResultList();
        }
        return clients;
    }
}
