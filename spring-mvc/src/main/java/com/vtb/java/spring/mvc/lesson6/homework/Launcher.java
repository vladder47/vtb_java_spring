package com.vtb.java.spring.mvc.lesson6.homework;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.URL;
import java.security.ProtectionDomain;

public class Launcher {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8189);

        ProtectionDomain domain = Launcher.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/app");
        webAppContext.setWar(location.toExternalForm());

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientProductConfig.class);
        HibernateSessionFactory hsf = context.getBean("hsf", HibernateSessionFactory.class);
        PrepareDataApp.prepareData(hsf);

        server.setHandler(webAppContext);
        server.start();
        server.join();
    }
}
