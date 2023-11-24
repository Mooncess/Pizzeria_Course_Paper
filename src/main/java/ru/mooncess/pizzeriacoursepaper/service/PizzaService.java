package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.PizzaCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Additive;
import ru.mooncess.pizzeriacoursepaper.entities.Dough;
import ru.mooncess.pizzeriacoursepaper.entities.Pizza;
import ru.mooncess.pizzeriacoursepaper.entities.Size;
import ru.mooncess.pizzeriacoursepaper.mappers.PizzaMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.AdditiveRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.DoughRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.SizeRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.pizza.PizzaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PizzaService {
    private final PizzaRepository pizzaRepository;
    private final DoughRepository doughRepository;
    private final SizeRepository sizeRepository;
    private final AdditiveRepository additiveRepository;
    private final PizzaMapper pizzaMapper = Mappers.getMapper(PizzaMapper.class);

    public List<Pizza> getAllPizza() {
        return pizzaRepository.findAll();
    }

    public Optional<Pizza> getPizzaById(Long id) {
        return pizzaRepository.findById(id);
    }

    public Optional<Pizza> createPizza(PizzaCreateDto pizza) {
        try {
            if (pizza.getPrice() > 0) {
                Pizza newPizza = pizzaMapper.pizzaCreateDtoToEntity(pizza);
//                newPizza.setId(pizzaRepository.save(newPizza).getId()); // This is necessary, because otherwise an entity with null values in the fields will be returned
//                return Optional.of(pizzaRepository.save(newPizza)); // I really don't understand what's going on here...
                List<Integer> tempList = new ArrayList<>();
                for (Size i : newPizza.getAvailableSizes()) {
                    tempList.add(i.getId());
                }
                List<Size> tempSize = sizeRepository.findAllById(tempList);
                tempList.clear();
                for (Dough i : newPizza.getAvailableDoughs()) {
                    tempList.add(i.getId());
                }
                List<Dough> tempDough = doughRepository.findAllById(tempList);
                List<Long> tempListForAdditives = new ArrayList<>();
                for (Additive i : newPizza.getAvailableAdditives()) {
                    tempListForAdditives.add(i.getId());
                }
                List<Additive> tempAdditive = additiveRepository.findAllById(tempListForAdditives);
                newPizza.setAvailableSizes(tempSize);
                newPizza.setAvailableDoughs(tempDough);
                newPizza.setAvailableAdditives(tempAdditive);
                return Optional.of(pizzaRepository.save(newPizza));
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<Pizza> updatePizza(Long id, PizzaCreateDto pizza) {
        Optional<Pizza> optionalPizza = pizzaRepository.findById(id);
        if (optionalPizza.isPresent()) {
            try {
                if (pizza.getPrice() > 0) {
                    Pizza updatedPizza = pizzaMapper.pizzaCreateDtoToEntity(pizza);
                    updatedPizza.setId(id);
                    return Optional.of(pizzaRepository.save(updatedPizza));
                }
            } catch (Exception e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean deletePizza(Long id) {
        Optional<Pizza> optionalPizza = pizzaRepository.findById(id);
        if (optionalPizza.isPresent()) {
            pizzaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
