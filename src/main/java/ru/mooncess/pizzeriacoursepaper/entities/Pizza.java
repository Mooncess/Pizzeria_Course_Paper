package ru.mooncess.pizzeriacoursepaper.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pizza extends Product{
    @ManyToMany(mappedBy = "productsUsingSize")
    private List<Size> availableSize;
    @ManyToMany(mappedBy = "productsUsingDough")
    private List<Dough> availableDough;
    @ManyToMany
    private List<Additive> availableAdditive;
}

