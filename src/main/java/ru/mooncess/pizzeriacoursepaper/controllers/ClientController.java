package ru.mooncess.pizzeriacoursepaper.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.mooncess.pizzeriacoursepaper.dto.PizzaPurchaseDto;
import ru.mooncess.pizzeriacoursepaper.dto.ProductToPurchaseCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.*;
import ru.mooncess.pizzeriacoursepaper.exceptions.AppError;
import ru.mooncess.pizzeriacoursepaper.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ClientController {
    public final SizeService sizeService;
    private final ComboService comboService;
    private final DessertService dessertService;
    private final DrinkService drinkService;
    private final HotService hotService;
    private final SnackService snackService;
    private final PizzaService pizzaService;
    private final ProductToPurchaseService productToPurchaseService;
    private final UserService userService;
    private final DoughService doughService;

    // Combo endpoints
    @GetMapping("/category/combo")
    public ResponseEntity<List<Combo>> getAllCombo(@RequestParam(name = "sort", required = false, defaultValue = "0") Integer sortPrice) {
        if (sortPrice == 0) {
            return ResponseEntity.ok(comboService.getAllCombo());
        }
        else if (sortPrice == 1) {
            return ResponseEntity.ok(comboService.findByOrderByPriceAsc());
        }
        else if (sortPrice == 2) {
            return ResponseEntity.ok(comboService.findByOrderByPriceDesc());
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/category/combo/{id}")
    public ResponseEntity<?> getComboById(@PathVariable Long id) {
        Optional<Combo> optionalCombo = comboService.getComboById(id);
        if (optionalCombo.isPresent()) {
            Combo combo = optionalCombo.get();
            return ResponseEntity.ok(combo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/category/combo/purchase/{id}")
    public ResponseEntity<?> purchaseCombo(@PathVariable Long id, @RequestParam Short quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Combo> comboOptional = comboService.getComboById(id);
        if (comboOptional.isPresent()) {
            Combo temp = comboOptional.get();
            Optional<ProductToPurchase> optionalProduct = productToPurchaseService.purchaseProduct(temp, quantity, username, temp.getAvailableDough(), temp.getAvailableSize(), null);
            if (optionalProduct.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(optionalProduct.get());
            }
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect data"), HttpStatus.BAD_REQUEST);
    }
    // Dessert endpoints
    @GetMapping("/category/dessert")
    public ResponseEntity<List<Dessert>> getAllDessert(@RequestParam(name = "sort", required = false, defaultValue = "0") Integer sortPrice) {
        if (sortPrice == 0) {
            return ResponseEntity.ok(dessertService.getAllDessert());
        }
        else if (sortPrice == 1) {
            return ResponseEntity.ok(dessertService.findByOrderByPriceAsc());
        }
        else if (sortPrice == 2) {
            return ResponseEntity.ok(dessertService.findByOrderByPriceDesc());
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/category/dessert/{id}")
    public ResponseEntity<?> getDessertById(@PathVariable Long id) {
        Optional<Dessert> optionalDessert = dessertService.getDessertById(id);
        if (optionalDessert.isPresent()) {
            Dessert dessert = optionalDessert.get();
            return ResponseEntity.ok(dessert);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/category/dessert/purchase/{id}")
    public ResponseEntity<?> purchaseDessert(@PathVariable Long id, @RequestParam Short quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Dessert> dessertOptional = dessertService.getDessertById(id);
        if (dessertOptional.isPresent()) {
            Dessert temp = dessertOptional.get();
            Optional<ProductToPurchase> optionalProduct = productToPurchaseService.purchaseProduct(temp, quantity, username, null, null, null);
            if (optionalProduct.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(optionalProduct.get());
            }
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect data"), HttpStatus.BAD_REQUEST);
    }
    // Drink endpoints
    @GetMapping("/category/drink")
    public ResponseEntity<List<Drink>> getAllDrink(@RequestParam(name = "sort", required = false, defaultValue = "0") Integer sortPrice) {
        if (sortPrice == 0) {
            return ResponseEntity.ok(drinkService.getAllDrink());
        }
        else if (sortPrice == 1) {
            return ResponseEntity.ok(drinkService.findByOrderByPriceAsc());
        }
        else if (sortPrice == 2) {
            return ResponseEntity.ok(drinkService.findByOrderByPriceDesc());
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/category/drink/{id}")
    public ResponseEntity<?> getDrinkById(@PathVariable Long id) {
        Optional<Drink> optionalDrink = drinkService.getDrinkById(id);
        if (optionalDrink.isPresent()) {
            Drink drink = optionalDrink.get();
            return ResponseEntity.ok(drink);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Hot endpoints
    @GetMapping("/category/hot")
    public ResponseEntity<List<Hot>> getAllHot(@RequestParam(name = "sort", required = false, defaultValue = "0") Integer sortPrice) {
        if (sortPrice == 0) {
            return ResponseEntity.ok(hotService.getAllHot());
        }
        else if (sortPrice == 1) {
            return ResponseEntity.ok(hotService.findByOrderByPriceAsc());
        }
        else if (sortPrice == 2) {
            return ResponseEntity.ok(hotService.findByOrderByPriceDesc());
        }
        return ResponseEntity.badRequest().build();
    }
    // Snack endpoints
    @GetMapping("/category/hot/{id}")
    public ResponseEntity<?> getHotById(@PathVariable Long id) {
        Optional<Hot> optionalHot = hotService.getHotById(id);
        if (optionalHot.isPresent()) {
            Hot hot = optionalHot.get();
            return ResponseEntity.ok(hot);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/category/snack")
    public ResponseEntity<List<Snack>> getAllSnack(@RequestParam(name = "sort", required = false, defaultValue = "0") Integer sortPrice) {
        if (sortPrice == 0) {
            return ResponseEntity.ok(snackService.getAllSnack());
        }
        else if (sortPrice == 1) {
            return ResponseEntity.ok(snackService.findByOrderByPriceAsc());
        }
        else if (sortPrice == 2) {
            return ResponseEntity.ok(snackService.findByOrderByPriceDesc());
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/category/snack/{id}")
    public ResponseEntity<?> getSnackById(@PathVariable Long id) {
        Optional<Snack> optionalSnack = snackService.getSnackById(id);
        if (optionalSnack.isPresent()) {
            Snack snack = optionalSnack.get();
            return ResponseEntity.ok(snack);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Pizza endpoints
    @GetMapping("/category/pizza")
    public ResponseEntity<List<Pizza>> getAllPizza(@RequestParam(name = "sort", required = false, defaultValue = "0") Integer sortPrice) {
        if (sortPrice == 0) {
            return ResponseEntity.ok(pizzaService.getAllPizza());
        }
        else if (sortPrice == 1) {
            return ResponseEntity.ok(pizzaService.findByOrderByPriceAsc());
        }
        else if (sortPrice == 2) {
            return ResponseEntity.ok(pizzaService.findByOrderByPriceDesc());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/category/pizza/{id}")
    public ResponseEntity<?> getPizzaById(@PathVariable Long id) {
        Optional<Pizza> optionalPizza = pizzaService.getPizzaById(id);
        if (optionalPizza.isPresent()) {
            Pizza pizza = optionalPizza.get();
            return ResponseEntity.ok(pizza);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/category/pizza/purchase/{id}")
    public ResponseEntity<?> purchasePizza(@PathVariable Long id, @RequestParam Short quantity, @RequestBody PizzaPurchaseDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Pizza> pizzaOptional = pizzaService.getPizzaById(id);
        if (pizzaOptional.isPresent()) {
            Pizza temp = pizzaOptional.get();
            Optional<Dough> optionalDough = doughService.getDoughById(dto.getDough());
            Optional<Size> optionalSize = sizeService.getSizeById(dto.getSize());
            if (optionalSize.isPresent() && optionalDough.isPresent()) {
                if (dto.getAdditives().isEmpty()) dto.setAdditives(null);
                Optional<ProductToPurchase> optionalProduct = productToPurchaseService.purchaseProduct(temp, quantity, username, optionalDough.get(), optionalSize.get(), dto.getAdditives());
                if (optionalProduct.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CREATED).body(optionalProduct.get());
                }
            }
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect data"), HttpStatus.BAD_REQUEST);
    }
    // Product endpoint
    @GetMapping("/category/all")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        productList.addAll(pizzaService.getAllPizza());
        productList.addAll(snackService.getAllSnack());
        productList.addAll(drinkService.getAllDrink());
        productList.addAll(dessertService.getAllDessert());
        productList.addAll(hotService.getAllHot());
        productList.addAll(comboService.getAllCombo());
        return ResponseEntity.ok(productList);
    }
}
