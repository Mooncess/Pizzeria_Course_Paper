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
public class Drink extends Product{
    @Column(nullable = true)
    private float volume;
}
