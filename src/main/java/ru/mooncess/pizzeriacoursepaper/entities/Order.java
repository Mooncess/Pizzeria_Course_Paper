package ru.mooncess.pizzeriacoursepaper.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String creationDate;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus status;
    @Column(nullable = false)
    private Float total;

    @ManyToMany
    private List<ProductToPurchase> orderItemList;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;
}