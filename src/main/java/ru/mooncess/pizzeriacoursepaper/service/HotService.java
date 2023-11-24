package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.HotCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Hot;
import ru.mooncess.pizzeriacoursepaper.mappers.HotMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.hot.HotRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotService {
    private final HotRepository hotRepository;
    private final HotMapper hotMapper;

    public List<Hot> getAllHot() {
        return hotRepository.findAll();
    }

    public Optional<Hot> getHotById(Long id) {
        return hotRepository.findById(id);
    }

    public Optional<Hot> createHot(HotCreateDto hot) {
        if (hot.getPrice() > 0) {
            Hot newHot = hotMapper.toEntity(hot);
            try {
                return Optional.of(hotRepository.getById(hotRepository.save(newHot).getId()));
            } catch (Exception e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public Optional<Hot> updateHot(Long id, HotCreateDto hot) {
        Optional<Hot> optionalHot = hotRepository.findById(id);
        if (optionalHot.isPresent()) {
            if (hot.getPrice() > 0) {
                Hot updatedHot = hotMapper.toEntity(hot);
                updatedHot.setId(id);
                try {
                    return Optional.of(hotRepository.save(updatedHot));
                } catch (Exception e) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    public boolean deleteHot(Long id) {
        Optional<Hot> optionalHot = hotRepository.findById(id);
        if (optionalHot.isPresent()) {
            hotRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
