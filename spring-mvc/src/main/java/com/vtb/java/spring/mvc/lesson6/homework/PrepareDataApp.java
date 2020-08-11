package com.vtb.java.spring.mvc.lesson6.homework;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
public class PrepareDataApp {
    private HibernateSessionFactory hibernateSessionFactory;

    @Autowired
    public void setHibernateSessionFactory(HibernateSessionFactory hibernateSessionFactory) {
        this.hibernateSessionFactory = hibernateSessionFactory;
    }

    @PostConstruct
    public void prepareData() {
        try (Session session = hibernateSessionFactory.getSession()) {
            String sql = Files.lines(Paths.get("lesson5_tables.sql")).collect(Collectors.joining(" "));
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
