package ru.mooncess.pizzeriacoursepaper.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dessert extends Product{
    @Column(nullable = false)
    private float weight;
}
