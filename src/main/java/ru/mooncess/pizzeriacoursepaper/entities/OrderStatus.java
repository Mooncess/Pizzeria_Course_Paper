package ru.mooncess.pizzeriacoursepaper.entities;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
