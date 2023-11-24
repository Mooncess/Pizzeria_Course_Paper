package ru.mooncess.pizzeriacoursepaper.mappers;

import org.mapstruct.Mapper;
import ru.mooncess.pizzeriacoursepaper.dto.PizzaCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Additive;
import ru.mooncess.pizzeriacoursepaper.entities.Dough;
import ru.mooncess.pizzeriacoursepaper.entities.Pizza;
import ru.mooncess.pizzeriacoursepaper.entities.Size;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PizzaMapper {
    default Pizza pizzaCreateDtoToEntity(PizzaCreateDto dto) {
        Pizza pizza = new Pizza();
        pizza.setTitle(dto.getTitle());
        pizza.setPrice(dto.getPrice());
        pizza.setDescription(dto.getDescription());

        // Маппинг свойства availableSize
        List<Size> sizes = new ArrayList<>();
        List<Integer> list = dto.getAvailableSizes();
        for (int i : list) {
            Size size = new Size();
            size.setId(i);
            sizes.add(size);
        }
        pizza.setAvailableSizes(sizes);

        // Маппинг свойства availableDough
        List<Dough> doughs = new ArrayList<>();
        list = dto.getAvailableDoughs();
        for (int i : list) {
            Dough dough = new Dough();
            dough.setId(i);
            doughs.add(dough);
        }
        pizza.setAvailableDoughs(doughs);

        // Маппинг свойства availableAdditives
        List<Additive> additives = new ArrayList<>();
        List<Long> listOfAdditive = dto.getAvailableAdditives();
        for (long i : listOfAdditive) {
            Additive additive = new Additive();
            additive.setId(i);
            additives.add(additive);
        }
        pizza.setAvailableAdditives(additives);
        return pizza;
    }
}
