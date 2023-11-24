package ru.mooncess.pizzeriacoursepaper.mappers;

import org.mapstruct.Mapper;
import ru.mooncess.pizzeriacoursepaper.dto.HotCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Hot;

@Mapper(componentModel = "spring")
public interface HotMapper {
    Hot toEntity(HotCreateDto dto);
}
