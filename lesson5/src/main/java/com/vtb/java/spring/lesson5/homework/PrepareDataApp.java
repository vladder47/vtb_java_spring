package com.vtb.java.spring.lesson5.homework;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
public class PrepareDataApp {
    public static void prepareData(HibernateSessionFactory hsf) {
        try (Session session = hsf.getSession()) {
            String sql = Files.lines(Paths.get("lesson5_tables.sql")).collect(Collectors.joining(" "));
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
