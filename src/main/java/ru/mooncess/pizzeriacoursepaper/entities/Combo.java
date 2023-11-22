package ru.mooncess.pizzeriacoursepaper.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Combo extends Product{
    @ManyToOne
    @JoinColumn(name = "size", nullable = true)
    private Size availableSize;
    @ManyToOne
    @JoinColumn(name = "dough", nullable = true)
    private Dough availableDough;
}

