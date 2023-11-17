package ru.mooncess.pizzeriacoursepaper.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Snack extends Product{
    @Column(nullable = false)
    private float weight;
}
