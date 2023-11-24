package ru.mooncess.pizzeriacoursepaper.dto;

import lombok.Data;

@Data
public class DessertCreateDto {
    private String title;
    private Float price;
    private String description;
    private Float weight;
}
