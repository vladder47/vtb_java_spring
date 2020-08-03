package com.vtb.java.spring.lesson5.homework;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class ProductRepository {
    private final HibernateSessionFactory hsf;

    @Autowired
    public ProductRepository(HibernateSessionFactory hsf) {
        this.hsf = hsf;
    }

    protected void saveProduct(Product product) {
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        }
    }

    protected void saveProducts(List<Product> products) {
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            for (Product product : products) {
                session.save(product);
            }
            session.getTransaction().commit();
        }
    }

    protected Product getProductById(long id) {
        Product product;
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            product = session.get(Product.class, id);
            session.getTransaction().commit();
        }
        return product;
    }

    protected List<Product> getAllProducts() {
        List<Product> products;
        try (Session session = hsf.getSession()) {
            session.beginTransaction();
            TypedQuery<Product> query = session.createQuery("SELECT p FROM Product p", Product.class);
            products = query.getResultList();
        }
        return products;
    }
}
