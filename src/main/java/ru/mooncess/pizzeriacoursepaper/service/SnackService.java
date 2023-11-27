package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.SnackCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Snack;
import ru.mooncess.pizzeriacoursepaper.mappers.SnackMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.snack.SnackRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SnackService {
    private final SnackRepository snackRepository;
    private final SnackMapper snackMapper;

    public List<Snack> getAllSnack() {
        return snackRepository.findAll();
    }

    public Optional<Snack> getSnackById(Long id) {
        return snackRepository.findById(id);
    }

    public Optional<Snack> createSnack(SnackCreateDto snack) {
        try {
            if (snack.getPrice() > 0 && snack.getWeight() > 0) {
                Snack newSnack = snackMapper.toEntity(snack);
                return Optional.of(snackRepository.getById(snackRepository.save(newSnack).getId()));
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<Snack> updateSnack(Long id, SnackCreateDto snack) {
        Optional<Snack> optionalSnack = snackRepository.findById(id);
        if (optionalSnack.isPresent()) {
            try {
                if (snack.getPrice() > 0 && snack.getWeight() > 0) {
                    Snack updatedSnack = snackMapper.toEntity(snack);
                    updatedSnack.setId(id);
                    return Optional.of(snackRepository.save(updatedSnack));
                }
            }catch (Exception e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean deleteSnack(Long id) {
        Optional<Snack> optionalSnack = snackRepository.findById(id);
        if (optionalSnack.isPresent()) {
            snackRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Snack> findByOrderByPriceAsc(){
        return snackRepository.findByOrderByPriceAsc();
    }

    public List<Snack> findByOrderByPriceDesc(){
        return snackRepository.findByOrderByPriceDesc();
    }
}
