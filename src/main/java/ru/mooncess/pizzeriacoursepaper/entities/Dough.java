package ru.mooncess.pizzeriacoursepaper.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "dough_product",
            joinColumns = @JoinColumn(name = "dough_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productsUsingDough;
}
