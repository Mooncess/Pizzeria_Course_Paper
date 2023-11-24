package ru.mooncess.pizzeriacoursepaper.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "dough")
public class Dough {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
