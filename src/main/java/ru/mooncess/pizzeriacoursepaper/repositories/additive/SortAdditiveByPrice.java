package ru.mooncess.pizzeriacoursepaper.repositories.additive;

import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Additive;

import java.util.List;

@Repository
public interface SortAdditiveByPrice {
    List<Additive> findByOrderByPriceAsc();
    List<Additive> findByOrderByPriceDesc();
}
