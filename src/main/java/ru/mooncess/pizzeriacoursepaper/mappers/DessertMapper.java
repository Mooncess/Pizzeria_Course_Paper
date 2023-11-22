package ru.mooncess.pizzeriacoursepaper.mappers;

import org.mapstruct.Mapper;
import ru.mooncess.pizzeriacoursepaper.dto.DessertCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Dessert;

@Mapper(componentModel = "spring")
public interface DessertMapper {
    Dessert toEntity(DessertCreateDto dto);
}
