package ru.mooncess.pizzeriacoursepaper.repositories.dessert;

import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Dessert;

import java.util.List;

@Repository
public interface SortDessertByPrice {
    List<Dessert> findByOrderByPriceAsc();
    List<Dessert> findByOrderByPriceDesc();
}
