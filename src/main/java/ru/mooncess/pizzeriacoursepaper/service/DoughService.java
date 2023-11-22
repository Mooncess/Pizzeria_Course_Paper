package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.entities.Dough;
import ru.mooncess.pizzeriacoursepaper.repositories.DoughRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoughService {
    private final DoughRepository doughRepository;
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
}
