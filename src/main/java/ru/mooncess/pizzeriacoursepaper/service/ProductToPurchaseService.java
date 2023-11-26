package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.entities.*;
import ru.mooncess.pizzeriacoursepaper.repositories.*;
import ru.mooncess.pizzeriacoursepaper.repositories.additive.AdditiveRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.pizza.PizzaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductToPurchaseService {
    private final ProductToPurchaseRepository repository;
    private final UserRepository userRepository;
    private final BasketRepository basketRepository;
    private final AdditiveRepository additiveRepository;
    private final PizzaRepository pizzaRepository;
    public Optional<ProductToPurchase> purchaseProduct(Product sourceProduct, Short quantity, String username, Dough dough, Size size, List<Long> additiveList) {
//        try {
//
//        } catch (Exception e) {
//            return Optional.empty();
//        }
        Basket basket = basketRepository.getById(userRepository.findByUsername(username).get().getBasket().getId());
        ProductToPurchase newProduct = new ProductToPurchase();
        newProduct.setProductId(sourceProduct.getId());
        newProduct.setTitle(sourceProduct.getTitle());
        newProduct.setQuantity(quantity);
        newProduct.setDough(dough);
        newProduct.setSize(size);

        ProductToPurchase productToPurchaseOptional;
        float priceAdditive = 0;
        if (additiveList != null) {
            newProduct.setAdditives(additiveRepository.findAllById(additiveList));
            for (Additive i : newProduct.getAdditives()) {
                // Проверка, что данная добавка реально существует для данной пиццы
                if (!pizzaRepository.getById(sourceProduct.getId()).getAvailableAdditives().contains(i)) {
                    return Optional.empty();
                }
                priceAdditive+=i.getPrice();
            }
        }
        else newProduct.setAdditives(Collections.emptyList());
        newProduct.setPrice((sourceProduct.getPrice() + priceAdditive) * quantity);
        // Проверяем, есть ли в корзине уже такой продукт
        for (ProductToPurchase i : basket.getBasketItemList()) {
            if (i.getProductId().equals(newProduct.getProductId()) &&
                    ((i.getDough() != null && i.getDough().equals(newProduct.getDough())) || (i.getDough() == null && newProduct.getDough() == null)) &&
                    ((i.getSize() != null && i.getSize().equals(newProduct.getSize())) || (i.getSize() == null && newProduct.getSize() == null)) &&
                    ((!i.getAdditives().isEmpty() && i.getAdditives().containsAll(newProduct.getAdditives()) && newProduct.getAdditives().containsAll(i.getAdditives())) || (i.getAdditives().isEmpty() && newProduct.getAdditives().isEmpty()))) {
                // Нашли такой продукт в корзине => увеличить итоговую стоимость и количество
                newProduct.setQuantity((short) (newProduct.getQuantity()+i.getQuantity()));
                newProduct.setPrice((sourceProduct.getPrice() + priceAdditive) * newProduct.getQuantity());
                List<ProductToPurchase> optionalList = repository.findAllByProductIdAndQuantityAndDoughAndSize(newProduct.getProductId(), newProduct.getQuantity(), newProduct.getDough(), newProduct.getSize());
                if (!optionalList.isEmpty()) {
                    for (ProductToPurchase j : optionalList) {
                        if ((!j.getAdditives().isEmpty() && j.getAdditives().equals(newProduct.getAdditives())) || (j.getAdditives().isEmpty() && newProduct.getAdditives() == null)) {
                            // В БД уже есть запись для данного продукта на продажу
                            basket.getBasketItemList().set(basket.getBasketItemList().indexOf(i), j);
                            basketRepository.save(basket);
                            return Optional.of(j);
                        }
                    }
                }
                // В БД еще нет такой записи для данного продукта на продажу
                newProduct = repository.save(newProduct);
                basket.getBasketItemList().set(basket.getBasketItemList().indexOf(i), newProduct);
                basketRepository.save(basket);
                return Optional.of(newProduct);
            }
        }
        // В корзине не лежит данный продукт
        // // В БД уже есть запись для данного продукта на продажу
        List<ProductToPurchase> optionalList = repository.findAllByProductIdAndQuantityAndDoughAndSize(newProduct.getProductId(), newProduct.getQuantity(), newProduct.getDough(), newProduct.getSize());
        if (!optionalList.isEmpty()) {
            for (ProductToPurchase j : optionalList) {
                if ((!j.getAdditives().isEmpty() && j.getAdditives().containsAll(newProduct.getAdditives()) && newProduct.getAdditives().containsAll(j.getAdditives())) || (j.getAdditives().isEmpty() && newProduct.getAdditives().isEmpty())) {
                    // В БД уже есть запись для данного продукта на продажу
                    basket.getBasketItemList().add(j);
                    basketRepository.save(basket);
                    return Optional.of(j);
                }
            }
        }
        // В БД еще нет такой записи для данного продукта на продажу
        newProduct = repository.save(newProduct);
        basket.getBasketItemList().add(newProduct);
        basketRepository.save(basket);
        return Optional.of(newProduct);
    }
}
