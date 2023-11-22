package ru.mooncess.pizzeriacoursepaper.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mooncess.pizzeriacoursepaper.dto.AdditiveCreateDto;
import ru.mooncess.pizzeriacoursepaper.dto.ComboCreateDto;
import ru.mooncess.pizzeriacoursepaper.dto.DessertCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.*;
import ru.mooncess.pizzeriacoursepaper.service.*;

import java.util.List;

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

    // Additive endpoints
    @GetMapping("/additive")
    public ResponseEntity<List<Additive>> getAllAdditives() {
        return additiveService.getAllAdditives();
    }

    @GetMapping("/additive/{id}")
    public ResponseEntity<?> getAdditiveById(@PathVariable Long id) {
        return additiveService.getAdditiveById(id);
    }

    @PostMapping("/additive")
    public ResponseEntity<?> createAdditive(@RequestBody AdditiveCreateDto additive) {
        return additiveService.createAdditive(additive);
    }

    @PutMapping("/additive/{id}")
    public ResponseEntity<?> updateAdditive(@PathVariable Long id, @RequestBody AdditiveCreateDto additive) {
        return additiveService.updateAdditive(id, additive);
    }

    @DeleteMapping("/additive/{id}")
    public ResponseEntity<Void> deleteAdditive(@PathVariable Long id) {
        return additiveService.deleteAdditive(id);
    }

    // Dough endpoints
    @GetMapping("/dough")
    public ResponseEntity<List<Dough>> getAllDough() {
        return doughService.getAllDough();
    }

    @PostMapping("/dough")
    public ResponseEntity<Dough> createDough(@RequestParam String name) {
        return doughService.createDough(name);
    }

    @PutMapping("/dough/{id}")
    public ResponseEntity<Dough> updateDough(@PathVariable Integer id, @RequestParam String name) {
        return doughService.updateDough(id, name);
    }

    @DeleteMapping("/dough/{id}")
    public ResponseEntity<Void> deleteDough(@PathVariable Integer id) {
        return doughService.deleteDough(id);
    }

    // Size endpoints
    @GetMapping("/size")
    public ResponseEntity<List<Size>> getAllSize() {
        return sizeService.getAllSize();
    }

    @PostMapping("/size")
    public ResponseEntity<Size> createSize(@RequestParam String name) {
        return sizeService.createSize(name);
    }

    @PutMapping("/size/{id}")
    public ResponseEntity<Size> updateSize(@PathVariable Integer id, @RequestParam String name) {
        return sizeService.updateSize(id, name);
    }

    @DeleteMapping("/size/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable Integer id) {
        return sizeService.deleteSize(id);
    }

    // Order status endpoints
    @GetMapping("/order-status")
    public ResponseEntity<List<OrderStatus>> getAllOrderStatus() {
        return orderStatusService.getAllOrderStatus();
    }

    @PostMapping("/order-status")
    public ResponseEntity<OrderStatus> createOrderStatus(@RequestParam String name) {
        return orderStatusService.createOrderStatus(name);
    }

    @PutMapping("/order-status/{id}")
    public ResponseEntity<OrderStatus> updateOrderStatus(@PathVariable Integer id, @RequestParam String name) {
        return orderStatusService.updateOrderStatus(id, name);
    }

    @DeleteMapping("/order-status/{id}")
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable Integer id) {
        return orderStatusService.deleteOrderStatus(id);
    }

    // Combo endpoints
    @GetMapping("/combo")
    public ResponseEntity<List<Combo>> getAllCombo() {
        return comboService.getAllCombo();
    }

    @GetMapping("/combo/{id}")
    public ResponseEntity<?> getComboById(@PathVariable Long id) {
        return comboService.getComboById(id);
    }

    @PostMapping("/combo")
    public ResponseEntity<?> createCombo(@RequestBody ComboCreateDto combo) {
        return comboService.createCombo(combo);
    }

    @PutMapping("/combo/{id}")
    public ResponseEntity<?> updateCombo(@PathVariable Long id, @RequestBody ComboCreateDto combo) {
        return comboService.updateCombo(id, combo);
    }

    @DeleteMapping("/combo/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable Long id) {
        return comboService.deleteCombo(id);
    }

    @GetMapping("/dessert")
    public ResponseEntity<List<Dessert>> getAllDessert() {
        return dessertService.getAllDessert();
    }

    @GetMapping("/dessert/{id}")
    public ResponseEntity<?> getDessertById(@PathVariable Long id) {
        return dessertService.getDessertById(id);
    }

    @PostMapping("/dessert")
    public ResponseEntity<?> createDessert(@RequestBody DessertCreateDto dessert) {
        return dessertService.createDessert(dessert);
    }

    @PutMapping("/dessert/{id}")
    public ResponseEntity<?> updateDessert(@PathVariable Long id, @RequestBody DessertCreateDto dessert) {
        return dessertService.updateDessert(id, dessert);
    }

    @DeleteMapping("/dessert/{id}")
    public ResponseEntity<Void> deleteDessert(@PathVariable Long id) {
        return dessertService.deleteDessert(id);
    }

}
