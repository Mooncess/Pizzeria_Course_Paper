package ru.mooncess.pizzeriacoursepaper.repositories.pizza;

import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Pizza;

import java.util.List;

@Repository
public interface SortPizzaByPrice {
    List<Pizza> findByOrderByPriceAsc();
    List<Pizza> findByOrderByPriceDesc();
}
