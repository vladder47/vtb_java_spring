package com.vtb.vladislav.spring.web.services.lesson12.homework.repositories;

import com.vtb.vladislav.spring.web.services.lesson12.homework.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
