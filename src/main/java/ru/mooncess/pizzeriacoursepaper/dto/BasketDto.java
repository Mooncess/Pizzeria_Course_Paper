package ru.mooncess.pizzeriacoursepaper.dto;

import lombok.Data;
import ru.mooncess.pizzeriacoursepaper.entities.ProductToPurchase;

import java.util.List;

@Data
public class BasketDto {
    private float total;
    private List<ProductToPurchase> basketList;
}
