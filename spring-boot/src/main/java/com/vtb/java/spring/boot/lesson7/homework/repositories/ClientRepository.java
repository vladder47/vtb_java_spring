package com.vtb.java.spring.boot.lesson7.homework.repositories;

import com.vtb.java.spring.boot.lesson7.homework.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ClientRepository extends JpaRepository<Client, Long> {
}
