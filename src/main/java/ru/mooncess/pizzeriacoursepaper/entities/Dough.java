package ru.mooncess.pizzeriacoursepaper.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "dough")
public class Dough {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Byte id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "dough_product",
            joinColumns = @JoinColumn(name = "dough_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productsUsingDough;
}
