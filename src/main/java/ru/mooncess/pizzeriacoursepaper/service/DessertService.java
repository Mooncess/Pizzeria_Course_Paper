package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.DessertCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Dessert;
import ru.mooncess.pizzeriacoursepaper.exceptions.AppError;
import ru.mooncess.pizzeriacoursepaper.mappers.DessertMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.dessert.DessertRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DessertService {
    private final DessertRepository dessertRepository;
    private final DessertMapper dessertMapper;

    public ResponseEntity<List<Dessert>> getAllDessert() {
        List<Dessert> desserts = dessertRepository.findAll();
        return ResponseEntity.ok(desserts);
    }
    public ResponseEntity<?> getDessertById(Long id) {
        Optional<Dessert> optionalDessert = dessertRepository.findById(id);
        if (optionalDessert.isPresent()) {
            Dessert dessert = optionalDessert.get();
            return ResponseEntity.ok(dessert);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<?> createDessert(DessertCreateDto dessert) {
        if (dessert.getPrice() <= 0 || dessert.getWeight() <= 0) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
        }
        Dessert newDessert = dessertMapper.toEntity(dessert);
        Dessert createdDessert = dessertRepository.save(newDessert);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDessert);
    }
    public ResponseEntity<?> updateDessert(Long id, DessertCreateDto dessert) {
        Optional<Dessert> optionalDessert = dessertRepository.findById(id);
        if (optionalDessert.isPresent()) {
            if (dessert.getPrice() <= 0 || dessert.getWeight() <= 0) {
                return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
            }
            Dessert updatedDessert = dessertMapper.toEntity(dessert);
            updatedDessert.setId(id);
            Dessert savedDessert = dessertRepository.save(updatedDessert);
            return ResponseEntity.ok(savedDessert);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Void> deleteDessert(Long id) {
        Optional<Dessert> optionalDessert = dessertRepository.findById(id);
        if (optionalDessert.isPresent()) {
            dessertRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
