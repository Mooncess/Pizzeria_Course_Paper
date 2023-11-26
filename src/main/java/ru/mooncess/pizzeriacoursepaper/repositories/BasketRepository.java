package ru.mooncess.pizzeriacoursepaper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Basket;
import ru.mooncess.pizzeriacoursepaper.entities.ProductToPurchase;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
}
