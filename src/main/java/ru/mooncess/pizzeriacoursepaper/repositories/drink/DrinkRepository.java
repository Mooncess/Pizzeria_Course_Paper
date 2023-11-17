package ru.mooncess.pizzeriacoursepaper.repositories.drink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Drink;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long>, SortDrinkByPrice {
}
