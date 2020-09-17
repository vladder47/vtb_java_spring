package com.vtb.vladislav.spring.data.lesson8.homework.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    public enum OrderStatus {
        PROCESSING("В обработке"),
        READY("Готово");

        private final String statusName;

        OrderStatus(String statusName) {
            this.statusName = statusName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private Collection<OrderItem> orderItems;
}
