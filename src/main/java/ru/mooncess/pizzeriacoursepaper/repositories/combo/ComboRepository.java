package ru.mooncess.pizzeriacoursepaper.repositories.combo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Combo;
import ru.mooncess.pizzeriacoursepaper.entities.Dessert;
import ru.mooncess.pizzeriacoursepaper.repositories.dessert.SortDessertByPrice;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Long>, SortComboByPrice {
}
