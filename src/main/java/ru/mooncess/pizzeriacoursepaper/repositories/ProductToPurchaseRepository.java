package ru.mooncess.pizzeriacoursepaper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductToPurchaseRepository extends JpaRepository<ProductToPurchase, Long> {
//    OrderItem findByDescriptionAndBasketId(String description, Long id);
//    List<OrderItem> findAllByBasketId(Long id);
//    OrderItem findByIdAndBasketId(Long id, Long basketId);
    Optional<ProductToPurchase> findByProductIdAndQuantityAndDoughAndSize(Long productId, Short quantity, Dough dough, Size size);
    List<ProductToPurchase> findAllByProductIdAndQuantityAndDoughAndSize(Long productId, Short quantity, Dough dough, Size size);
}

