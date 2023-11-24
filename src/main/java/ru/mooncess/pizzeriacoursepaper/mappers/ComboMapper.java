package ru.mooncess.pizzeriacoursepaper.mappers;

import org.mapstruct.Mapper;
import ru.mooncess.pizzeriacoursepaper.dto.ComboCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Combo;
import ru.mooncess.pizzeriacoursepaper.entities.Dough;
import ru.mooncess.pizzeriacoursepaper.entities.Size;

@Mapper
public interface ComboMapper {
    default Combo comboCreateDtoToEntity(ComboCreateDto dto) {
        Combo combo = new Combo();
        combo.setTitle(dto.getTitle());
        combo.setPrice(dto.getPrice());
        combo.setDescription(dto.getDescription());

        // Маппинг свойства availableSize
        Size size = new Size();
        size.setId(dto.getAvailableSize());
        combo.setAvailableSize(size);

        // Маппинг свойства availableDough
        Dough dough = new Dough();
        dough.setId(dto.getAvailableDough());
        combo.setAvailableDough(dough);

        return combo;
    }
}

