package ru.mooncess.pizzeriacoursepaper.dto;

import lombok.Data;

@Data
public class ProductToPurchaseCreateDto {
    private Long productId;
    private Short quantity;
}
