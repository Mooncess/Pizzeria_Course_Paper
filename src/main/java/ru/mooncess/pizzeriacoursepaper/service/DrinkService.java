package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.DrinkCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Drink;
import ru.mooncess.pizzeriacoursepaper.mappers.DrinkMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.drink.DrinkRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DrinkService {
    private final DrinkRepository drinkRepository;
    private final DrinkMapper drinkMapper;

    public List<Drink> getAllDrink() {
        return drinkRepository.findAll();
    }

    public Optional<Drink> getDrinkById(Long id) {
        return drinkRepository.findById(id);
    }

    public Optional<Drink> createDrink(DrinkCreateDto drink) {
        if (drink.getPrice() > 0 && drink.getVolume() > 0) {
            Drink newDrink = drinkMapper.toEntity(drink);
            try {
                return Optional.of(drinkRepository.getById(drinkRepository.save(newDrink).getId()));
            } catch (Exception e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public Optional<Drink> updateDrink(Long id, DrinkCreateDto drink) {
        Optional<Drink> optionalDrink = drinkRepository.findById(id);
        if (optionalDrink.isPresent()) {
            if (drink.getPrice() > 0 && drink.getVolume() > 0) {
                Drink updatedDrink = drinkMapper.toEntity(drink);
                updatedDrink.setId(id);
                try {
                    return Optional.of(drinkRepository.save(updatedDrink));
                } catch (Exception e) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    public boolean deleteDrink(Long id) {
        Optional<Drink> optionalDrink = drinkRepository.findById(id);
        if (optionalDrink.isPresent()) {
            drinkRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
