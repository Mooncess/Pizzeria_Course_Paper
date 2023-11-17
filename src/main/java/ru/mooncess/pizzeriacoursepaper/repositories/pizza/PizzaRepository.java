package ru.mooncess.pizzeriacoursepaper.repositories.pizza;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long>, SortPizzaByPrice {
}
