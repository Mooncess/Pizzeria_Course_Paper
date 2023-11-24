package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.AdditiveCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Additive;
import ru.mooncess.pizzeriacoursepaper.mappers.AdditiveMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.additive.AdditiveRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdditiveService {
    private final AdditiveRepository additiveRepository;
    private final AdditiveMapper additiveMapper;

    public List<Additive> getAllAdditives() {
        return additiveRepository.findAll();
    }
    public Optional<Additive> getAdditiveById(Long id) {
        return additiveRepository.findById(id);
    }
    public Optional<Additive> createAdditive(AdditiveCreateDto additive) {
        if (additive.getPrice() <= 0 ) {
            return Optional.empty();
        }
        Additive newAdditive = additiveMapper.toEntity(additive);
        return Optional.of(additiveRepository.save(newAdditive));
    }
    public Optional<Additive> updateAdditive(Long id, AdditiveCreateDto additive) {
        Optional<Additive> optionalAdditive = additiveRepository.findById(id);
        if (optionalAdditive.isPresent()) {
            if (additive.getPrice() > 0) {
                Additive update = additiveMapper.toEntity(additive);
                update.setId(id);
                return Optional.of(additiveRepository.save(update));
            }
        }
        return Optional.empty();
    }

    public boolean deleteAdditive(Long id) {
        Optional<Additive> optionalAdditive = additiveRepository.findById(id);
        if (optionalAdditive.isPresent()) {
            additiveRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Additive> findByOrderByPriceAsc(){
        return additiveRepository.findByOrderByPriceAsc();
    }

    public List<Additive> findByOrderByPriceDesc(){
        return additiveRepository.findByOrderByPriceDesc();
    }
}
