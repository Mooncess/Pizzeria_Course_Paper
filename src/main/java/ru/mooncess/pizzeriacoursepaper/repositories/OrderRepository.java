package ru.mooncess.pizzeriacoursepaper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mooncess.pizzeriacoursepaper.entities.Order;
import ru.mooncess.pizzeriacoursepaper.entities.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStatus(OrderStatus status);
}
