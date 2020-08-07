package com.vtb.java.spring.lesson5.homework;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class ClientRepository {
    private final HibernateSessionFactory hsf;

    @Autowired
    public ClientRepository(HibernateSessionFactory hsf) {
        this.hsf = hsf;
    }

    protected void saveClient(Client client) {
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
        }
    }

    protected void saveClients(List<Client> clients) {
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            for (Client client : clients) {
                session.save(client);
            }
            session.getTransaction().commit();
        }
    }

    protected Client getClientById(long id) {
        Client client;
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            client = session.get(Client.class, id);
            session.getTransaction().commit();
        }
        return client;
    }

    protected List<Client> getAllClients() {
        List<Client> clients;
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            TypedQuery<Client> query = session.createQuery("SELECT c FROM Client c", Client.class);
            clients = query.getResultList();
        }
        return clients;
    }
}
