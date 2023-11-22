package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.AdditiveCreateDto;
import ru.mooncess.pizzeriacoursepaper.dto.ComboCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.*;
import ru.mooncess.pizzeriacoursepaper.exceptions.AppError;
import ru.mooncess.pizzeriacoursepaper.mappers.AdditiveMapper;
import ru.mooncess.pizzeriacoursepaper.mappers.ComboMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.AdditiveRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.DoughRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.OrderStatusRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.SizeRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.combo.ComboRepository;

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

    private final ComboRepository comboRepository;
    private final ComboMapper comboMapper = Mappers.getMapper(ComboMapper.class);

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
    public ResponseEntity<?> createAdditive(AdditiveCreateDto additive) {
        if (additive.getPrice() <= 0 ) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
        }
        Additive newAdditive = additiveMapper.toEntity(additive);
        Additive createdAdditive = additiveRepository.save(newAdditive);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdditive);
    }
    public ResponseEntity<?> updateAdditive(Long id, AdditiveCreateDto additive) {
        Optional<Additive> optionalAdditive = additiveRepository.findById(id);
        if (optionalAdditive.isPresent()) {
            if (additive.getPrice() <= 0 ) {
                return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
            }
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

    public ResponseEntity<List<Combo>> getAllCombo() {
        List<Combo> combos = comboRepository.findAll();
        return ResponseEntity.ok(combos);
    }
    public ResponseEntity<?> getComboById(Long id) {
        Optional<Combo> optionalCombo = comboRepository.findById(id);
        if (optionalCombo.isPresent()) {
            Combo combo = optionalCombo.get();
            return ResponseEntity.ok(combo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<?> createCombo(ComboCreateDto combo) {
        if (combo.getPrice() <= 0) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
        }
        Combo newCombo = comboMapper.ComboCreateDtoToEntity(combo);
        try {
            Combo createdCombo = comboRepository.save(newCombo);
            createdCombo = comboRepository.getById(createdCombo.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCombo);
        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> updateCombo(Long id, ComboCreateDto combo) {
        Optional<Combo> optionalCombo = comboRepository.findById(id);
        if (optionalCombo.isPresent()) {
            if (combo.getPrice() <= 0) {
                return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
            }
            Combo updatedCombo = comboMapper.ComboCreateDtoToEntity(combo);
            updatedCombo.setId(id);
            try {
                Combo savedCombo = comboRepository.save(updatedCombo);
                return ResponseEntity.ok(savedCombo);
            } catch (Exception e) {
                return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Void> deleteCombo(Long id) {
        Optional<Combo> optionalCombo = comboRepository.findById(id);
        if (optionalCombo.isPresent()) {
            comboRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
