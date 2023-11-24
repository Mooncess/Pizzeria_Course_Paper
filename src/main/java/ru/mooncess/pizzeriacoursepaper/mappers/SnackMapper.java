package ru.mooncess.pizzeriacoursepaper.mappers;

import org.mapstruct.Mapper;
import ru.mooncess.pizzeriacoursepaper.dto.SnackCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Snack;

@Mapper(componentModel = "spring")
public interface SnackMapper {
    Snack toEntity(SnackCreateDto dto);
}
