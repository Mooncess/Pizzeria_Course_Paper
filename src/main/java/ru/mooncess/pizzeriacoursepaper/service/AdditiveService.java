package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.AdditiveCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Additive;
import ru.mooncess.pizzeriacoursepaper.exceptions.AppError;
import ru.mooncess.pizzeriacoursepaper.mappers.AdditiveMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.AdditiveRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdditiveService {
    private final AdditiveRepository additiveRepository;
    private final AdditiveMapper additiveMapper;

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
}
