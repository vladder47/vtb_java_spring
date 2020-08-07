package com.vtb.java.spring.mvc.lesson6.homework;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component(value = "hsf")
public class HibernateSessionFactory {
    private final SessionFactory sessionFactory;

    public HibernateSessionFactory() {
        this.sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        return new Configuration()
                .configure("configs/lesson6/hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void shutdown() {
        sessionFactory.close();
    }
}
