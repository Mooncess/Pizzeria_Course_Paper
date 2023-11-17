package ru.mooncess.pizzeriacoursepaper.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mooncess.pizzeriacoursepaper.dto.AdditiveCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Additive;
import ru.mooncess.pizzeriacoursepaper.service.AdminService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    // Additive's endpoints
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
}
