package ru.mooncess.pizzeriacoursepaper.mappers;

import org.mapstruct.Mapper;
import ru.mooncess.pizzeriacoursepaper.dto.AdditiveCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Additive;

@Mapper(componentModel = "spring")
public interface AdditiveMapper {
    Additive toEntity(AdditiveCreateDto dto);
}
