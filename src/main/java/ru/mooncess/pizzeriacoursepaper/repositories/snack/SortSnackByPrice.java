package ru.mooncess.pizzeriacoursepaper.repositories.snack;

import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Snack;

import java.util.List;

@Repository
public interface SortSnackByPrice {
    List<Snack> findByOrderByPriceAsc();
    List<Snack> findByOrderByPriceDesc();
}
