package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.entities.Dough;
import ru.mooncess.pizzeriacoursepaper.repositories.DoughRepository;
import ru.mooncess.pizzeriacoursepaper.service.DoughService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoughServiceTest {

    @Mock
    private DoughRepository doughRepository;

    private DoughService doughService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doughService = new DoughService(doughRepository);
    }

    @Test
    void getAllDough_ShouldReturnListOfDough() {
        // Arrange
        List<Dough> doughList = new ArrayList<>();
        Dough dough1 = new Dough();
        dough1.setId(1);
        Dough dough2 = new Dough();
        dough2.setId(2);
        doughList.add(dough1);
        doughList.add(dough2);

        when(doughRepository.findAll()).thenReturn(doughList);

        // Act
        List<Dough> result = doughService.getAllDough();

        // Assert
        assertEquals(doughList.size(), result.size());
        for (int i = 0; i < doughList.size(); i++) {
            assertEquals(doughList.get(i), result.get(i));
        }
        verify(doughRepository, times(1)).findAll();
    }

    @Test
    void getDoughById_WithExistingId_ShouldReturnDoughOptional() {
        // Arrange
        int id = 1;
        Dough dough = new Dough();
        dough.setId(id);

        when(doughRepository.findById(id)).thenReturn(Optional.of(dough));

        // Act
        Optional<Dough> result = doughService.getDoughById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(dough, result.get());
        verify(doughRepository, times(1)).findById(id);
    }

    @Test
    void getDoughById_WithNonExistingId_ShouldReturnEmptyOptional() {
        // Arrange
        int id = 1;

        when(doughRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Dough> result = doughService.getDoughById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(doughRepository, times(1)).findById(id);
    }

    // Другие тесты для остальных методов класса DoughService

    @Test
    void deleteDough_WithExistingId_ShouldReturnTrue() {
        // Arrange
        int id = 1;
        Dough dough = new Dough();
        dough.setId(id);

        when(doughRepository.findById(id)).thenReturn(Optional.of(dough));

        // Act
        boolean result = doughService.deleteDough(id);

        // Assert
        assertTrue(result);
        verify(doughRepository, times(1)).findById(id);
        verify(doughRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteDough_WithNonExistingId_ShouldReturnFalse() {
        // Arrange
        int id = 1;

        when(doughRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = doughService.deleteDough(id);

        // Assert
        assertFalse(result);
        verify(doughRepository, times(1)).findById(id);
        verify(doughRepository, never()).deleteById(any());
    }
}
