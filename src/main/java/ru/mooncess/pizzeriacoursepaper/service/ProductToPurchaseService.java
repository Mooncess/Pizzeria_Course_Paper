package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.entities.*;
import ru.mooncess.pizzeriacoursepaper.repositories.*;
import ru.mooncess.pizzeriacoursepaper.repositories.additive.AdditiveRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductToPurchaseService {
    private final ProductToPurchaseRepository repository;
    private final UserRepository userRepository;
    private final BasketRepository basketRepository;
    private final AdditiveRepository additiveRepository;
    public Optional<ProductToPurchase> purchaseProduct(Product sourceProduct, Short quantity, String username, Dough dough, Size size, List<Long> additiveList) {
//        try {
//
//        } catch (Exception e) {
//            return Optional.empty();
//        }
        Basket basket = basketRepository.getById(userRepository.findByUsername(username).get().getBasket().getId());
        ProductToPurchase newProduct = new ProductToPurchase();
        newProduct.setProductId(sourceProduct.getId());
        newProduct.setQuantity(quantity);
        newProduct.setPrice(sourceProduct.getPrice() * quantity);
        newProduct.setDough(dough);
        newProduct.setSize(size);

        Optional<ProductToPurchase> productToPurchaseOptional;
        float priceAdditive = 0;

        if (additiveList != null) {
            newProduct.setAdditives(additiveRepository.findAllById(additiveList));
            for (Additive i : newProduct.getAdditives()) {
                priceAdditive+=i.getPrice();

            }
            newProduct.setPrice(newProduct.getPrice() + priceAdditive);
            List<ProductToPurchase> optionalList = repository.findAllByProductIdAndQuantityAndDoughAndSize(newProduct.getProductId(), newProduct.getQuantity(), newProduct.getDough(), newProduct.getSize());
            if (!optionalList.isEmpty()) {
                for (ProductToPurchase i : optionalList) {
                    System.out.println(i.getAdditives());
                    if (i.getAdditives().equals(newProduct.getAdditives())) {
                        productToPurchaseOptional = Optional.of(i);
                    }
                }
            }
        }

        for (ProductToPurchase i : basket.getBasketItemList()) {
            if (i.getProductId().equals(newProduct.getProductId()) &&
                    ((i.getDough() != null && i.getDough().equals(newProduct.getDough())) || (i.getDough() == null && newProduct.getDough() == null)) &&
                    ((i.getSize() != null && i.getSize().equals(newProduct.getSize())) || (i.getSize() == null && newProduct.getSize() == null)) &&
                    ((!i.getAdditives().isEmpty() && i.getAdditives().equals(newProduct.getAdditives())) || (i.getAdditives().isEmpty() && newProduct.getAdditives() == null))) {
                newProduct.setQuantity((short) (newProduct.getQuantity()+i.getQuantity()));
                newProduct.setPrice((sourceProduct.getPrice() + priceAdditive) * newProduct.getQuantity());
                productToPurchaseOptional = repository.findByProductIdAndQuantityAndDoughAndSize(newProduct.getProductId(), newProduct.getQuantity(), newProduct.getDough(), newProduct.getSize());
                if (productToPurchaseOptional.isPresent()) {
                    basket.getBasketItemList().set(basket.getBasketItemList().indexOf(i), productToPurchaseOptional.get());
                    basketRepository.save(basket);
                    return productToPurchaseOptional;
                }
                newProduct = repository.save(newProduct);
                basket.getBasketItemList().set(basket.getBasketItemList().indexOf(i), newProduct);
                basketRepository.save(basket);
                return Optional.of(newProduct);
            }
        }
        if (productToPurchaseOptional.isPresent()) {
            basket.getBasketItemList().add(productToPurchaseOptional.get());
            basketRepository.save(basket);
            return productToPurchaseOptional;
        }
        newProduct = repository.save(newProduct);
        basket.getBasketItemList().add(newProduct);
        basketRepository.save(basket);
        return Optional.of(newProduct);
    }

    private Optional<ProductToPurchase> situationOne(ProductToPurchase newProduct) {

    }
}
