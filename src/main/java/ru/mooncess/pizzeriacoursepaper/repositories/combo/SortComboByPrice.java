package ru.mooncess.pizzeriacoursepaper.repositories.combo;

import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Combo;

import java.util.List;

@Repository
public interface SortComboByPrice {
    List<Combo> findByOrderByPriceAsc();
    List<Combo> findByOrderByPriceDesc();
}
