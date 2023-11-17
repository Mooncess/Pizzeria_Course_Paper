package ru.mooncess.pizzeriacoursepaper.repositories.hot;

import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Hot;

import java.util.List;

@Repository
public interface SortHotByPrice {
    List<Hot> findByOrderByPriceAsc();
    List<Hot> findByOrderByPriceDesc();
}
