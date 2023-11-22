package ru.mooncess.pizzeriacoursepaper.dto;

import lombok.Data;

@Data
public class ComboCreateDto {
    private String title;
    protected Float price;
    private String description;
    private Integer availableSize;
    private Integer availableDough;
}
