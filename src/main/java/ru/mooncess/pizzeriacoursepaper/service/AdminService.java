package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.AdditiveCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Additive;
import ru.mooncess.pizzeriacoursepaper.entities.Dough;
import ru.mooncess.pizzeriacoursepaper.entities.OrderStatus;
import ru.mooncess.pizzeriacoursepaper.entities.Size;
import ru.mooncess.pizzeriacoursepaper.mappers.AdditiveMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.AdditiveRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.DoughRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.OrderStatusRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.SizeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdditiveRepository additiveRepository;
    private final AdditiveMapper additiveMapper;

    private final DoughRepository doughRepository;

    public final SizeRepository sizeRepository;

    private final OrderStatusRepository orderStatusRepository;

    public ResponseEntity<List<Additive>> getAllAdditives() {
        List<Additive> additives = additiveRepository.findAll();
        return ResponseEntity.ok(additives);
    }
    public ResponseEntity<?> getAdditiveById(Long id) {
        Optional<Additive> optionalAdditive = additiveRepository.findById(id);
        if (optionalAdditive.isPresent()) {
            Additive additive = optionalAdditive.get();
            return ResponseEntity.ok(additive);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Additive> createAdditive(AdditiveCreateDto additive) {
        Additive newAdditive = additiveMapper.toEntity(additive);
        Additive createdAdditive = additiveRepository.save(newAdditive);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdditive);
    }
    public ResponseEntity<Additive> updateAdditive(Long id, AdditiveCreateDto additive) {
        Optional<Additive> optionalAdditive = additiveRepository.findById(id);
        if (optionalAdditive.isPresent()) {
            Additive updatedAdditive = additiveMapper.toEntity(additive);
            updatedAdditive.setId(id);
            Additive savedAdditive = additiveRepository.save(updatedAdditive);
            return ResponseEntity.ok(savedAdditive);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Void> deleteAdditive(Long id) {
        Optional<Additive> optionalAdditive = additiveRepository.findById(id);
        if (optionalAdditive.isPresent()) {
            additiveRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<List<Dough>> getAllDough() {
        List<Dough> doughs = doughRepository.findAll();
        return ResponseEntity.ok(doughs);
    }
    public ResponseEntity<Dough> createDough(String name) {
        Dough dough = new Dough();
        dough.setName(name);
        Dough createdDough = doughRepository.save(dough);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDough);
    }
    public ResponseEntity<Dough> updateDough(Integer id, String name) {
        Optional<Dough> optionalDough = doughRepository.findById(id);
        if (optionalDough.isPresent()) {
            Dough updatedDough = doughRepository.getById(id);
            updatedDough.setName(name);
            Dough savedDough = doughRepository.save(updatedDough);
            return ResponseEntity.ok(savedDough);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Void> deleteDough(Integer id) {
        Optional<Dough> optionalDough = doughRepository.findById(id);
        if (optionalDough.isPresent()) {
            doughRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<List<Size>> getAllSize() {
        List<Size> sizes = sizeRepository.findAll();
        return ResponseEntity.ok(sizes);
    }
    public ResponseEntity<Size> createSize(String name) {
        Size size = new Size();
        size.setName(name);
        Size createdSize = sizeRepository.save(size);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSize);
    }
    public ResponseEntity<Size> updateSize(Integer id, String name) {
        Optional<Size> optionalSize = sizeRepository.findById(id);
        if (optionalSize.isPresent()) {
            Size updatedSize = sizeRepository.getById(id);
            updatedSize.setName(name);
            Size savedSize = sizeRepository.save(updatedSize);
            return ResponseEntity.ok(savedSize);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Void> deleteSize(Integer id) {
        Optional<Size> optionalSize = sizeRepository.findById(id);
        if (optionalSize.isPresent()) {
            sizeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<List<OrderStatus>> getAllOrderStatus() {
        List<OrderStatus> orderStatuses = orderStatusRepository.findAll();
        return ResponseEntity.ok(orderStatuses);
    }
    public ResponseEntity<?> getOrderStatusById(Integer id) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findById(id);
        if (optionalOrderStatus.isPresent()) {
            OrderStatus orderStatus = optionalOrderStatus.get();
            return ResponseEntity.ok(orderStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
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
