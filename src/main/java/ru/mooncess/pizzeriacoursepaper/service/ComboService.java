package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.ComboCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Combo;
import ru.mooncess.pizzeriacoursepaper.exceptions.AppError;
import ru.mooncess.pizzeriacoursepaper.mappers.ComboMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.combo.ComboRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComboService {
    private final ComboRepository comboRepository;
    private final ComboMapper comboMapper = Mappers.getMapper(ComboMapper.class);

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
