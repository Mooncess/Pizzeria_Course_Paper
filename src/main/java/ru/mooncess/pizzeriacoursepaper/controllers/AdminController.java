package ru.mooncess.pizzeriacoursepaper.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mooncess.pizzeriacoursepaper.dto.AdditiveCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.*;
import ru.mooncess.pizzeriacoursepaper.service.AdminService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    // Additive endpoints
    @GetMapping("/additive")
    public ResponseEntity<List<Additive>> getAllAdditives() {
        return adminService.getAllAdditives();
    }

    @GetMapping("/additive/{id}")
    public ResponseEntity<?> getAdditiveById(@PathVariable Long id) {
        return adminService.getAdditiveById(id);
    }

    @PostMapping("/additive")
    public ResponseEntity<Additive> createAdditive(@RequestBody AdditiveCreateDto additive) {
        return adminService.createAdditive(additive);
    }

    @PutMapping("/additive/{id}")
    public ResponseEntity<Additive> updateAdditive(@PathVariable Long id, @RequestBody AdditiveCreateDto additive) {
        return adminService.updateAdditive(id, additive);
    }

    @DeleteMapping("/additive/{id}")
    public ResponseEntity<Void> deleteAdditive(@PathVariable Long id) {
        return adminService.deleteAdditive(id);
    }

    // Dough endpoints
    @GetMapping("/dough")
    public ResponseEntity<List<Dough>> getAllDough() {
        return adminService.getAllDough();
    }

    @PostMapping("/dough")
    public ResponseEntity<Dough> createDough(@RequestParam String name) {
        return adminService.createDough(name);
    }

    @PutMapping("/dough/{id}")
    public ResponseEntity<Dough> updateDough(@PathVariable Integer id, @RequestParam String name) {
        return adminService.updateDough(id, name);
    }

    @DeleteMapping("/dough/{id}")
    public ResponseEntity<Void> deleteDough(@PathVariable Integer id) {
        return adminService.deleteDough(id);
    }

    // Size endpoints
    @GetMapping("/size")
    public ResponseEntity<List<Size>> getAllSize() {
        return adminService.getAllSize();
    }

    @PostMapping("/size")
    public ResponseEntity<Size> createSize(@RequestParam String name) {
        return adminService.createSize(name);
    }

    @PutMapping("/size/{id}")
    public ResponseEntity<Size> updateSize(@PathVariable Integer id, @RequestParam String name) {
        return adminService.updateSize(id, name);
    }

    @DeleteMapping("/size/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable Integer id) {
        return adminService.deleteSize(id);
    }

    // Order status endpoints
    @GetMapping("/order-status")
    public ResponseEntity<List<OrderStatus>> getAllOrderStatus() {
        return adminService.getAllOrderStatus();
    }

    @PostMapping("/order-status")
    public ResponseEntity<OrderStatus> createOrderStatus(@RequestParam String name) {
        return adminService.createOrderStatus(name);
    }

    @PutMapping("/order-status/{id}")
    public ResponseEntity<OrderStatus> updateOrderStatus(@PathVariable Integer id, @RequestParam String name) {
        return adminService.updateOrderStatus(id, name);
    }

    @DeleteMapping("/order-status/{id}")
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable Integer id) {
        return adminService.deleteOrderStatus(id);
    }
}
