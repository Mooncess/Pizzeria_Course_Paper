package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.entities.Hot;
import ru.mooncess.pizzeriacoursepaper.mappers.HotMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.hot.HotRepository;
import ru.mooncess.pizzeriacoursepaper.service.HotService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotServiceTest {

    @Mock
    private HotRepository hotRepository;

    @Mock
    private HotMapper hotMapper;

    private HotService hotService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hotService = new HotService(hotRepository, hotMapper);
    }

    @Test
    void getAllHot_ShouldReturnListOfHot() {
        // Arrange
        List<Hot> hotList = new ArrayList<>();
        Hot hot1 = new Hot();
        hot1.setId(1L);
        Hot hot2 = new Hot();
        hot2.setId(2L);
        hotList.add(hot1);
        hotList.add(hot2);

        when(hotRepository.findAll()).thenReturn(hotList);

        // Act
        List<Hot> result = hotService.getAllHot();

        // Assert
        assertEquals(hotList.size(), result.size());
        for (int i = 0; i < hotList.size(); i++) {
            assertEquals(hotList.get(i), result.get(i));
        }
        verify(hotRepository, times(1)).findAll();
    }

    @Test
    void getHotById_WithExistingId_ShouldReturnHotOptional() {
        // Arrange
        Long id = 1L;
        Hot hot = new Hot();
        hot.setId(id);

        when(hotRepository.findById(id)).thenReturn(Optional.of(hot));

        // Act
        Optional<Hot> result = hotService.getHotById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(hot, result.get());
        verify(hotRepository, times(1)).findById(id);
    }

    @Test
    void getHotById_WithNonExistingId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;

        when(hotRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Hot> result = hotService.getHotById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(hotRepository, times(1)).findById(id);
    }

    // Другие тесты для остальных методов класса HotService

    @Test
    void deleteHot_WithExistingId_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Hot hot = new Hot();
        hot.setId(id);

        when(hotRepository.findById(id)).thenReturn(Optional.of(hot));

        // Act
        boolean result = hotService.deleteHot(id);

        // Assert
        assertTrue(result);
        verify(hotRepository, times(1)).findById(id);
        verify(hotRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteHot_WithNonExistingId_ShouldReturnFalse() {
        // Arrange
        Long id = 1L;

        when(hotRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = hotService.deleteHot(id);

        // Assert
        assertFalse(result);
        verify(hotRepository, times(1)).findById(id);
        verify(hotRepository, never()).deleteById(any());
    }
}
