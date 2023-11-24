package ru.mooncess.pizzeriacoursepaper.dto;

import lombok.Data;

import java.util.List;

@Data
public class PizzaCreateDto {
    private String title;
    private Float price;
    private String description;
    private List<Integer> availableSizes;
    private List<Integer> availableDoughs;
    private List<Long> availableAdditives;
}
