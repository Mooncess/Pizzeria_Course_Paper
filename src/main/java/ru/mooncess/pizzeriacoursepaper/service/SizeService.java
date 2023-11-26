package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.entities.Dough;
import ru.mooncess.pizzeriacoursepaper.entities.Size;
import ru.mooncess.pizzeriacoursepaper.repositories.SizeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SizeService {
    private final SizeRepository sizeRepository;

    public List<Size> getAllSize() {
        return sizeRepository.findAll();
    }

    public Optional<Size> getSizeById(int id) {
        return sizeRepository.findById(id);
    }

    public Optional<Size> createSize(String name) {
        Size size = new Size();
        size.setName(name);
        return Optional.of(sizeRepository.save(size));
    }

    public Optional<Size> updateSize(Integer id, String name) {
        Optional<Size> optionalSize = sizeRepository.findById(id);
        if (optionalSize.isPresent()) {
            Size update = new Size();
            update.setName(name);
            update.setId(id);
            return Optional.of(sizeRepository.save(update));
        }
        return Optional.empty();
    }

    public boolean deleteSize(Integer id) {
        Optional<Size> optionalSize = sizeRepository.findById(id);
        if (optionalSize.isPresent()) {
            sizeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
