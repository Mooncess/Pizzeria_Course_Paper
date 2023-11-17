package ru.mooncess.pizzeriacoursepaper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mooncess.pizzeriacoursepaper.entities.ProductToPurchase;

import java.util.List;

public interface ProductToPurchaseRepository extends JpaRepository<ProductToPurchase, Long> {
//    OrderItem findByDescriptionAndBasketId(String description, Long id);
//    List<OrderItem> findAllByBasketId(Long id);
//    OrderItem findByIdAndBasketId(Long id, Long basketId);
}
