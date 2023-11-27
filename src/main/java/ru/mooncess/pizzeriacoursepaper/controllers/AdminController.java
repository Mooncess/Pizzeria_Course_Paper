package ru.mooncess.pizzeriacoursepaper.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mooncess.pizzeriacoursepaper.dto.*;
import ru.mooncess.pizzeriacoursepaper.entities.*;
import ru.mooncess.pizzeriacoursepaper.exceptions.AppError;
import ru.mooncess.pizzeriacoursepaper.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdditiveService additiveService;
    private final DoughService doughService;
    public final SizeService sizeService;
    private final OrderStatusService orderStatusService;
    private final ComboService comboService;
    private final DessertService dessertService;
    private final DrinkService drinkService;
    private final HotService hotService;
    private final SnackService snackService;
    private final PizzaService pizzaService;

    // Additive endpoints
    @GetMapping("/additive")
    public ResponseEntity<List<Additive>> getAllAdditives(@RequestParam(name = "sort", required = false, defaultValue = "0") Integer sortPrice) {
        if (sortPrice == 0) {
            return ResponseEntity.ok(additiveService.getAllAdditives());
        }
        else if (sortPrice == 1) {
            return ResponseEntity.ok(additiveService.findByOrderByPriceAsc());
        }
        else if (sortPrice == 2) {
            return ResponseEntity.ok(additiveService.findByOrderByPriceDesc());
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/additive/{id}")
    public ResponseEntity<?> getAdditiveById(@PathVariable Long id) {
        Optional<Additive> optionalAdditive = additiveService.getAdditiveById(id);
        if (optionalAdditive.isPresent()) {
            Additive additive = optionalAdditive.get();
            return ResponseEntity.ok(additive);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/additive")
    public ResponseEntity<?> createAdditive(@RequestBody AdditiveCreateDto additive) {
        Optional<Additive> optionalAdditive = additiveService.createAdditive(additive);
        if (optionalAdditive.isPresent()) {
            Additive createdAdditive = optionalAdditive.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAdditive);
        }
        else {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/additive/{id}")
    public ResponseEntity<?> updateAdditive(@PathVariable Long id, @RequestBody AdditiveCreateDto additive) {
        Optional<Additive> update = additiveService.updateAdditive(id, additive);
        if (update.isPresent()) {
            return ResponseEntity.ok(update.get());
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/additive/{id}")
    public ResponseEntity<Void> deleteAdditive(@PathVariable Long id) {
        if (additiveService.deleteAdditive(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Dough endpoints
    @GetMapping("/dough")
    public ResponseEntity<List<Dough>> getAllDough() {
        return ResponseEntity.ok(doughService.getAllDough());
    }
    @PostMapping("/dough")
    public ResponseEntity<?> createDough(@RequestParam String name) {
        Optional<Dough> optionalDough = doughService.createDough(name);
        if (optionalDough.isPresent()) {
            Dough createdDough = optionalDough.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDough);
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/dough/{id}")
    public ResponseEntity<?> updateDough(@PathVariable Integer id, @RequestParam String name) {
        Optional<Dough> update = doughService.updateDough(id, name);
        if (update.isPresent()) {
            return ResponseEntity.ok(update.get());
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/dough/{id}")
    public ResponseEntity<Void> deleteDough(@PathVariable Integer id) {
        if (doughService.deleteDough(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Size endpoints
    @GetMapping("/size")
    public ResponseEntity<List<Size>> getAllSize() {
        return ResponseEntity.ok(sizeService.getAllSize());
    }
    @PostMapping("/size")
    public ResponseEntity<?> createSize(@RequestParam String name) {
        Optional<Size> optionalSize = sizeService.createSize(name);
        if (optionalSize.isPresent()) {
            Size createdSize = optionalSize.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSize);
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/size/{id}")
    public ResponseEntity<?> updateSize(@PathVariable Integer id, @RequestParam String name) {
        Optional<Size> update = sizeService.updateSize(id, name);
        if (update.isPresent()) {
            return ResponseEntity.ok(update.get());
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/size/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable Integer id) {
        if (sizeService.deleteSize(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    // Order status endpoints
    @GetMapping("/order-status")
    public ResponseEntity<List<OrderStatus>> getAllOrderStatus() {
        return ResponseEntity.ok(orderStatusService.getAllOrderStatus());
    }
    @PostMapping("/order-status")
    public ResponseEntity<?> createOrderStatus(@RequestParam String name) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusService.createOrderStatus(name);
        if (optionalOrderStatus.isPresent()) {
            OrderStatus createdOrderStatus = optionalOrderStatus.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderStatus);
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/order-status/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Integer id, @RequestParam String name) {
        Optional<OrderStatus> update = orderStatusService.updateOrderStatus(id, name);
        if (update.isPresent()) {
            return ResponseEntity.ok(update.get());
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/order-status/{id}")
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable Integer id) {
        if (orderStatusService.deleteOrderStatus(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    // Combo endpoints
    @PostMapping("/combo")
    public ResponseEntity<?> createCombo(@RequestBody ComboCreateDto combo) {
        Optional<Combo> optionalCombo = comboService.createCombo(combo);
        if (optionalCombo.isPresent()) {
            Combo createdCombo = optionalCombo.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCombo);
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/combo/{id}")
    public ResponseEntity<?> updateCombo(@PathVariable Long id, @RequestBody ComboCreateDto combo) {
        Optional<Combo> update = comboService.updateCombo(id, combo);
        if (update.isPresent()) {
            return ResponseEntity.ok(update.get());
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);

    }
    @DeleteMapping("/combo/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable Long id) {
        if (comboService.deleteCombo(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    // Dessert endpoints
    @PostMapping("/dessert")
    public ResponseEntity<?> createDessert(@RequestBody DessertCreateDto dessert) {
        Optional<Dessert> optionalDessert = dessertService.createDessert(dessert);
        if (optionalDessert.isPresent()) {
            Dessert createdDessert = optionalDessert.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDessert);
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/dessert/{id}")
    public ResponseEntity<?> updateDessert(@PathVariable Long id, @RequestBody DessertCreateDto dessert) {
        Optional<Dessert> update = dessertService.updateDessert(id, dessert);
        if (update.isPresent()) {
            return ResponseEntity.ok(update.get());
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/dessert/{id}")
    public ResponseEntity<Void> deleteDessert(@PathVariable Long id) {
        if (dessertService.deleteDessert(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    // Drink endpoints
    @PostMapping("/drink")
    public ResponseEntity<?> createDrink(@RequestBody DrinkCreateDto drink) {
        Optional<Drink> optionalDrink = drinkService.createDrink(drink);
        if (optionalDrink.isPresent()) {
            Drink createdDrink = optionalDrink.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDrink);
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/drink/{id}")
    public ResponseEntity<?> updateDrink(@PathVariable Long id, @RequestBody DrinkCreateDto drink) {
        Optional<Drink> update = drinkService.updateDrink(id, drink);
        if (update.isPresent()) {
            return ResponseEntity.ok(update.get());
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/drink/{id}")
    public ResponseEntity<Void> deleteDrink(@PathVariable Long id) {
        if (drinkService.deleteDrink(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    // Hot endpoints
    @PostMapping("/hot")
    public ResponseEntity<?> createHot(@RequestBody HotCreateDto hot) {
        Optional<Hot> optionalHot = hotService.createHot(hot);
        if (optionalHot.isPresent()) {
            Hot createdHot = optionalHot.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(createdHot);
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/hot/{id}")
    public ResponseEntity<?> updateHot(@PathVariable Long id, @RequestBody HotCreateDto hot) {
        Optional<Hot> update = hotService.updateHot(id, hot);
        if (update.isPresent()) {
            return ResponseEntity.ok(update.get());
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/hot/{id}")
    public ResponseEntity<Void> deleteHot(@PathVariable Long id) {
        if (hotService.deleteHot(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Snack endpoints
    @PostMapping("/snack")
    public ResponseEntity<?> createSnack(@RequestBody SnackCreateDto snack) {
        Optional<Snack> optionalSnack = snackService.createSnack(snack);
        if (optionalSnack.isPresent()) {
            Snack createdSnack = optionalSnack.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSnack);
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/snack/{id}")
    public ResponseEntity<?> updateSnack(@PathVariable Long id, @RequestBody SnackCreateDto snack) {
        Optional<Snack> update = snackService.updateSnack(id, snack);
        if (update.isPresent()) {
            return ResponseEntity.ok(update.get());
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/snack/{id}")
    public ResponseEntity<Void> deleteSnack(@PathVariable Long id) {
        if (snackService.deleteSnack(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Pizza endpoints
    @PostMapping("/pizza")
    public ResponseEntity<?> createPizza(@RequestBody PizzaCreateDto pizza) {
        Optional<Pizza> optionalPizza = pizzaService.createPizza(pizza);
        if (optionalPizza.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalPizza.get());
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect creation data"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/pizza/{id}")
    public ResponseEntity<?> updatePizza(@PathVariable Long id, @RequestBody PizzaCreateDto pizza) {
        Optional<Pizza> update = pizzaService.updatePizza(id, pizza);
        if (update.isPresent()) {
            return ResponseEntity.ok(update.get());
        }
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid data to update"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/pizza/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {
        if (pizzaService.deletePizza(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
