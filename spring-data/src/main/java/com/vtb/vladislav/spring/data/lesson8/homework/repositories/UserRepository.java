package com.vtb.vladislav.spring.data.lesson8.homework.repositories;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
