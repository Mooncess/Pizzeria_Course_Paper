package ru.mooncess.pizzeriacoursepaper.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductToPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Short quantity;
    @Column(nullable = false)
    private float price;
    @ManyToOne
    @JoinColumn(name = "size")
    private Size size;
    @ManyToOne
    @JoinColumn(name = "dough")
    private Dough dough;
    @ManyToMany
    private List<Additive> additives;
}
