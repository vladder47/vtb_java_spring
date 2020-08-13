package com.vtb.vladislav.spring.data.lesson8.homework.repositories;

import com.vtb.vladislav.spring.data.lesson8.homework.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
