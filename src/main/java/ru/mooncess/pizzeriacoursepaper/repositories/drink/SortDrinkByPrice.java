package ru.mooncess.pizzeriacoursepaper.repositories.drink;

import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Drink;

import java.util.List;

@Repository
public interface SortDrinkByPrice {
    List<Drink> findByOrderByPriceAsc();
    List<Drink> findByOrderByPriceDesc();
}