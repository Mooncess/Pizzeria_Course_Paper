package ru.mooncess.pizzeriacoursepaper.mappers;

import org.mapstruct.Mapper;
import ru.mooncess.pizzeriacoursepaper.dto.ClientOrderDto;
import ru.mooncess.pizzeriacoursepaper.dto.PizzaCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {
    default ClientOrderDto entityToClientDto(Order entity) {
        ClientOrderDto dto = new ClientOrderDto();
        dto.setId(entity.getId());
        dto.setAddress(entity.getAddress());
        dto.setCreationDate(entity.getCreationDate());
        dto.setStatus(entity.getStatus().getName());
        dto.setTotal(entity.getTotal());
        dto.setOrderItemList(entity.getOrderItemList());
        return dto;
    }
}
