package com.vtb.vladislav.spring.data.lesson8.homework.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "publish_year")
    private Integer publishYear;

//    @OneToMany(mappedBy = "book")
//    private Collection<OrderItem> orderItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) &&
                title.equals(book.title) &&
                description.equals(book.description) &&
                genre.equals(book.genre) &&
                price.equals(book.price) &&
                publishYear.equals(book.publishYear);
    }
}
