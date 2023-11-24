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
    @ManyToMany
    private List<Size> availableSizes;
    @ManyToMany
    private List<Dough> availableDoughs;
    @ManyToMany
    private List<Additive> availableAdditives;
}

