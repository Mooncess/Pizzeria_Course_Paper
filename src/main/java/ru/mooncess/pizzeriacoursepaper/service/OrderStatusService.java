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

    public List<OrderStatus> getAllOrderStatus() {
        return orderStatusRepository.findAll();
    }

    public Optional<OrderStatus> createOrderStatus(String name) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setName(name);
        return Optional.of(orderStatusRepository.save(orderStatus));
    }

    public Optional<OrderStatus> updateOrderStatus(Integer id, String name) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findById(id);
        if (optionalOrderStatus.isPresent()) {
            OrderStatus update = new OrderStatus();
            update.setName(name);
            update.setId(id);
            return Optional.of(orderStatusRepository.save(update));
        }
        return Optional.empty();
    }

    public boolean deleteOrderStatus(Integer id) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findById(id);
        if (optionalOrderStatus.isPresent()) {
            orderStatusRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
