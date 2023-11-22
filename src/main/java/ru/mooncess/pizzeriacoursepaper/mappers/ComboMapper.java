package ru.mooncess.pizzeriacoursepaper.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.mooncess.pizzeriacoursepaper.dto.ComboCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Combo;
import ru.mooncess.pizzeriacoursepaper.entities.Dough;
import ru.mooncess.pizzeriacoursepaper.entities.Size;
import ru.mooncess.pizzeriacoursepaper.repositories.combo.ComboRepository;

@Mapper
public interface ComboMapper {
    default Combo ComboCreateDtoToEntity(ComboCreateDto dto) {
        Combo combo = new Combo();
        combo.setTitle(dto.getTitle());
        combo.setPrice(dto.getPrice());
        combo.setDescription(dto.getDescription());

        // Маппинг свойства availableSize
        Size size = new Size();
        size.setId(dto.getAvailableSize()); // Предполагается, что у Size есть свойство id
        combo.setAvailableSize(size);

        // Маппинг свойства availableDough
        Dough dough = new Dough();
        dough.setId(dto.getAvailableDough()); // Предполагается, что у Dough есть свойство id
        combo.setAvailableDough(dough);

        return combo;
    }
}

