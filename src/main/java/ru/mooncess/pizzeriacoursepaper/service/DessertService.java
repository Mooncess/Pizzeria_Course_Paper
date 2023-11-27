package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.DessertCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Dessert;
import ru.mooncess.pizzeriacoursepaper.mappers.DessertMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.dessert.DessertRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DessertService {
    private final DessertRepository dessertRepository;
    private final DessertMapper dessertMapper;

    public List<Dessert> getAllDessert() {
        return dessertRepository.findAll();
    }

    public Optional<Dessert> getDessertById(Long id) {
        return dessertRepository.findById(id);
    }

    public Optional<Dessert> createDessert(DessertCreateDto dessert) {
        try {
            if (dessert.getPrice() > 0 && dessert.getWeight() > 0) {
                Dessert newDessert = dessertMapper.toEntity(dessert);
                return Optional.of(dessertRepository.getById(dessertRepository.save(newDessert).getId()));
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<Dessert> updateDessert(Long id, DessertCreateDto dessert) {
        Optional<Dessert> optionalDessert = dessertRepository.findById(id);
        if (optionalDessert.isPresent()) {
            try {
                if (dessert.getPrice() > 0 && dessert.getWeight() > 0) {
                    Dessert updatedDessert = dessertMapper.toEntity(dessert);
                    updatedDessert.setId(id);
                    return Optional.of(dessertRepository.save(updatedDessert));
                }
            } catch (Exception e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean deleteDessert(Long id) {
        Optional<Dessert> optionalDessert = dessertRepository.findById(id);
        if (optionalDessert.isPresent()) {
            dessertRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Dessert> findByOrderByPriceAsc(){
        return dessertRepository.findByOrderByPriceAsc();
    }

    public List<Dessert> findByOrderByPriceDesc(){
        return dessertRepository.findByOrderByPriceDesc();
    }
}
