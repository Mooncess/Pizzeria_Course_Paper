package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.entities.Additive;
import ru.mooncess.pizzeriacoursepaper.mappers.AdditiveMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.additive.AdditiveRepository;
import ru.mooncess.pizzeriacoursepaper.service.AdditiveService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdditiveServiceTest {

    @Mock
    private AdditiveRepository additiveRepository;

    @Mock
    private AdditiveMapper additiveMapper;

    private AdditiveService additiveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        additiveService = new AdditiveService(additiveRepository, additiveMapper);
    }

    @Test
    void getAllAdditives_ShouldReturnListOfAdditives() {
        // Arrange
        List<Additive> additiveList = new ArrayList<>();
        Additive additive1 = new Additive();
        additive1.setId(1L);
        Additive additive2 = new Additive();
        additive2.setId(2L);
        additiveList.add(additive1);
        additiveList.add(additive2);

        when(additiveRepository.findAll()).thenReturn(additiveList);

        // Act
        List<Additive> result = additiveService.getAllAdditives();

        // Assert
        assertEquals(additiveList.size(), result.size());
        for (int i = 0; i < additiveList.size(); i++) {
            assertEquals(additiveList.get(i), result.get(i));
        }
        verify(additiveRepository, times(1)).findAll();
    }

    @Test
    void getAdditiveById_WithExistingId_ShouldReturnAdditiveOptional() {
        // Arrange
        Long id = 1L;
        Additive additive = new Additive();
        additive.setId(id);

        when(additiveRepository.findById(id)).thenReturn(Optional.of(additive));

        // Act
        Optional<Additive> result = additiveService.getAdditiveById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(additive, result.get());
        verify(additiveRepository, times(1)).findById(id);
    }

    @Test
    void getAdditiveById_WithNonExistingId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;

        when(additiveRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Additive> result = additiveService.getAdditiveById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(additiveRepository, times(1)).findById(id);
    }

    // Другие тесты для остальных методов класса AdditiveService

    @Test
    void deleteAdditive_WithExistingId_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Additive additive = new Additive();
        additive.setId(id);

        when(additiveRepository.findById(id)).thenReturn(Optional.of(additive));

        // Act
        boolean result = additiveService.deleteAdditive(id);

        // Assert
        assertTrue(result);
        verify(additiveRepository, times(1)).findById(id);
        verify(additiveRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteAdditive_WithNonExistingId_ShouldReturnFalse() {
        // Arrange
        Long id = 1L;

        when(additiveRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = additiveService.deleteAdditive(id);

        // Assert
        assertFalse(result);
        verify(additiveRepository, times(1)).findById(id);
        verify(additiveRepository, never()).deleteById(any());
    }
}
