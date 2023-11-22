package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.entities.Size;
import ru.mooncess.pizzeriacoursepaper.repositories.SizeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SizeService {
    public final SizeRepository sizeRepository;

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
}
