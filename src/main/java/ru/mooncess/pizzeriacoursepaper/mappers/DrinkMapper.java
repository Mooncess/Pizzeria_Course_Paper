package ru.mooncess.pizzeriacoursepaper.mappers;

import org.mapstruct.Mapper;
import ru.mooncess.pizzeriacoursepaper.entities.Drink;
import ru.mooncess.pizzeriacoursepaper.dto.DrinkCreateDto;

@Mapper(componentModel = "spring")
public interface DrinkMapper {
    Drink toEntity(DrinkCreateDto dto);
}
