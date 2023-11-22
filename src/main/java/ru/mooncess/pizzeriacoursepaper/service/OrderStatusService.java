package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.entities.OrderStatus;
import ru.mooncess.pizzeriacoursepaper.repositories.OrderStatusRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public ResponseEntity<List<OrderStatus>> getAllOrderStatus() {
        List<OrderStatus> orderStatuses = orderStatusRepository.findAll();
        return ResponseEntity.ok(orderStatuses);
    }
    public ResponseEntity<OrderStatus> createOrderStatus(String name) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setName(name);
        OrderStatus createdOrderStatus = orderStatusRepository.save(orderStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderStatus);
    }
    public ResponseEntity<OrderStatus> updateOrderStatus(Integer id, String name) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findById(id);
        if (optionalOrderStatus.isPresent()) {
            OrderStatus updatedOrderStatus = orderStatusRepository.getById(id);
            updatedOrderStatus.setName(name);
            OrderStatus savedOrderStatus = orderStatusRepository.save(updatedOrderStatus);
            return ResponseEntity.ok(savedOrderStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Void> deleteOrderStatus(Integer id) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findById(id);
        if (optionalOrderStatus.isPresent()) {
            orderStatusRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
