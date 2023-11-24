package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.ComboCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Combo;
import ru.mooncess.pizzeriacoursepaper.mappers.ComboMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.DoughRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.SizeRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.combo.ComboRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComboService {
    private final ComboRepository comboRepository;
    private final DoughRepository doughRepository;
    private final SizeRepository sizeRepository;
    private final ComboMapper comboMapper = Mappers.getMapper(ComboMapper.class);

    public List<Combo> getAllCombo() {
        return comboRepository.findAll();
    }
    public Optional<Combo> getComboById(Long id) {
        return comboRepository.findById(id);
    }

    public Optional<Combo> createCombo(ComboCreateDto combo) {
        try {
            if (combo.getPrice() > 0) {
                Combo newCombo = comboMapper.comboCreateDtoToEntity(combo);
                newCombo.setAvailableSize(sizeRepository.findById(newCombo.getAvailableSize().getId()).get());
                newCombo.setAvailableDough(doughRepository.findById(newCombo.getAvailableDough().getId()).get());
                return Optional.of(comboRepository.save(newCombo));
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();
    }
    public Optional<Combo> updateCombo(Long id, ComboCreateDto combo) {
        Optional<Combo> optionalCombo = comboRepository.findById(id);
        if (optionalCombo.isPresent()) {
            try {
                if (combo.getPrice() > 0) {
                    Combo updatedCombo = comboMapper.comboCreateDtoToEntity(combo);
                    updatedCombo.setId(id);
                    return Optional.of(comboRepository.save(updatedCombo));
                }
            } catch (Exception e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
    public boolean deleteCombo(Long id) {
        Optional<Combo> optionalCombo = comboRepository.findById(id);
        if (optionalCombo.isPresent()) {
            comboRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
