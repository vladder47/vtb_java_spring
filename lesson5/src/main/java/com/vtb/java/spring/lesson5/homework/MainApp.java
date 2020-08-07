package com.vtb.java.spring.lesson5.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientProductConfig.class);

        HibernateSessionFactory hsf = context.getBean("hsf", HibernateSessionFactory.class);
        PrepareDataApp.prepareData(hsf);
        ClientService clientService = context.getBean("clientService", ClientService.class);
        ProductService productService = context.getBean("productService", ProductService.class);

        Client c = new Client("Alexey", 40L);
        Product p = new Product("headphones", 500L);
        clientService.saveClient(c);
        productService.saveProduct(p);

        System.out.println("Пользователь с id = 1: " + clientService.getClientById(1));
        System.out.println("Продукт с id = 2: " + productService.getProductById(2));
        System.out.println();

        for (Client client : clientService.getAllClients()) {
            System.out.println(client);
        }
        for (Product product : productService.getAllProducts()) {
            System.out.println(product);
        }

        context.close();
    }
}
