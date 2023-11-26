package ru.mooncess.pizzeriacoursepaper.dto;

import lombok.Data;

import java.util.List;

@Data
public class PizzaPurchaseDto {
    private Integer dough;
    private Integer size;
    private List<Long> additives;
}
