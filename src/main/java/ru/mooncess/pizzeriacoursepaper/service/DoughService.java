package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.entities.Dough;
import ru.mooncess.pizzeriacoursepaper.repositories.DoughRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoughService {
    private final DoughRepository doughRepository;

    public List<Dough> getAllDough() {
        return doughRepository.findAll();
    }

    public Optional<Dough> getDoughById(int id) {
        return doughRepository.findById(id);
    }

    public Optional<Dough> createDough(String name) {
        Dough dough = new Dough();
        dough.setName(name);
        return Optional.of(doughRepository.save(dough));
    }

    public Optional<Dough> updateDough(Integer id, String name) {
        Optional<Dough> optionalDough = doughRepository.findById(id);
        if (optionalDough.isPresent()) {
            Dough update = new Dough();
            update.setName(name);
            update.setId(id);
            return Optional.of(doughRepository.save(update));
        }
        return Optional.empty();
    }

    public boolean deleteDough(Integer id) {
        Optional<Dough> optionalDough = doughRepository.findById(id);
        if (optionalDough.isPresent()) {
            doughRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
