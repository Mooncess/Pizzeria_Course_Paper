package ru.mooncess.pizzeriacoursepaper.dto;

import lombok.Data;
import ru.mooncess.pizzeriacoursepaper.entities.ProductToPurchase;

import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long client;
    private String address;
    private String creationDate;
    private String status;
    private Float total;
    private List<ProductToPurchase> orderItemList;
}
