package ru.mooncess.pizzeriacoursepaper.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mooncess.pizzeriacoursepaper.dto.*;
import ru.mooncess.pizzeriacoursepaper.entities.*;
import ru.mooncess.pizzeriacoursepaper.exceptions.AppError;
import ru.mooncess.pizzeriacoursepaper.service.*;

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
    public ResponseEntity<List<Additive>> getAllAdditives() {
        return ResponseEntity.ok(additiveService.getAllAdditives());
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
    @GetMapping("/orderStatus")
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
    @GetMapping("/combo")
    public ResponseEntity<List<Combo>> getAllCombo() {
        return ResponseEntity.ok(comboService.getAllCombo());
    }

    @GetMapping("/combo/{id}")
    public ResponseEntity<?> getComboById(@PathVariable Long id) {
        Optional<Combo> optionalCombo = comboService.getComboById(id);
        if (optionalCombo.isPresent()) {
            Combo combo = optionalCombo.get();
            return ResponseEntity.ok(combo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
    @GetMapping("/dessert")
    public ResponseEntity<List<Dessert>> getAllDessert() {
        return ResponseEntity.ok(dessertService.getAllDessert());
    }

    @GetMapping("/dessert/{id}")
    public ResponseEntity<?> getDessertById(@PathVariable Long id) {
        Optional<Dessert> optionalDessert = dessertService.getDessertById(id);
        if (optionalDessert.isPresent()) {
            Dessert dessert = optionalDessert.get();
            return ResponseEntity.ok(dessert);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
    @GetMapping("/drink")
    public ResponseEntity<List<Drink>> getAllDrink() {
        return ResponseEntity.ok(drinkService.getAllDrink());
    }

    @GetMapping("/drink/{id}")
    public ResponseEntity<?> getDrinkById(@PathVariable Long id) {
        Optional<Drink> optionalDrink = drinkService.getDrinkById(id);
        if (optionalDrink.isPresent()) {
            Drink drink = optionalDrink.get();
            return ResponseEntity.ok(drink);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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

    @GetMapping("/hot")
    public ResponseEntity<List<Hot>> getAllHot() {
        return ResponseEntity.ok(hotService.getAllHot());
    }

    @GetMapping("/hot/{id}")
    public ResponseEntity<?> getHotById(@PathVariable Long id) {
        Optional<Hot> optionalHot = hotService.getHotById(id);
        if (optionalHot.isPresent()) {
            Hot hot = optionalHot.get();
            return ResponseEntity.ok(hot);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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

    @GetMapping("/snack")
    public ResponseEntity<List<Snack>> getAllSnack() {
        return ResponseEntity.ok(snackService.getAllSnack());
    }

    @GetMapping("/snack/{id}")
    public ResponseEntity<?> getSnackById(@PathVariable Long id) {
        Optional<Snack> optionalSnack = snackService.getSnackById(id);
        if (optionalSnack.isPresent()) {
            Snack snack = optionalSnack.get();
            return ResponseEntity.ok(snack);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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

    @GetMapping("/pizza")
    public ResponseEntity<List<Pizza>> getAllPizza() {
        return ResponseEntity.ok(pizzaService.getAllPizza());
    }

    @GetMapping("/pizza/{id}")
    public ResponseEntity<?> getPizzaById(@PathVariable Long id) {
        Optional<Pizza> optionalPizza = pizzaService.getPizzaById(id);
        if (optionalPizza.isPresent()) {
            Pizza pizza = optionalPizza.get();
            return ResponseEntity.ok(pizza);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
