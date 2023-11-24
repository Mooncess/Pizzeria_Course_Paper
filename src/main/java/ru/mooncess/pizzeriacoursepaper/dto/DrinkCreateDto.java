package ru.mooncess.pizzeriacoursepaper.dto;

import lombok.Data;

@Data
public class DrinkCreateDto{
    private String title;
    private Float price;
    private String description;
    private Float volume;
}