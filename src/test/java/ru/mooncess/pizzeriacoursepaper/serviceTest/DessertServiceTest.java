package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.dto.DessertCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Dessert;
import ru.mooncess.pizzeriacoursepaper.mappers.DessertMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.dessert.DessertRepository;
import ru.mooncess.pizzeriacoursepaper.service.DessertService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DessertServiceTest {

    @Mock
    private DessertRepository dessertRepository;

    @Mock
    private DessertMapper dessertMapper;

    private DessertService dessertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dessertService = new DessertService(dessertRepository, dessertMapper);
    }

    @Test
    void getAllDessert_ShouldReturnAllDesserts() {
        // Arrange
        List<Dessert> expectedDesserts = new ArrayList<>();
        expectedDesserts.add(new Dessert());
        expectedDesserts.add(new Dessert());

        when(dessertRepository.findAll()).thenReturn(expectedDesserts);

        // Act
        List<Dessert> actualDesserts = dessertService.getAllDessert();

        // Assert
        assertEquals(expectedDesserts, actualDesserts);
        verify(dessertRepository, times(1)).findAll();
    }

    @Test
    void getDessertById_WithValidId_ShouldReturnDessert() {
        // Arrange
        Long id = 1L;
        Dessert expectedDessert = new Dessert();
        expectedDessert.setId(id);

        when(dessertRepository.findById(id)).thenReturn(Optional.of(expectedDessert));

        // Act
        Optional<Dessert> actualDessert = dessertService.getDessertById(id);

        // Assert
        assertTrue(actualDessert.isPresent());
        assertEquals(expectedDessert, actualDessert.get());
        verify(dessertRepository, times(1)).findById(id);
    }

    @Test
    void getDessertById_WithInvalidId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;

        when(dessertRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Dessert> actualDessert = dessertService.getDessertById(id);

        // Assert
        assertFalse(actualDessert.isPresent());
        verify(dessertRepository, times(1)).findById(id);
    }

    @Test
    void createDessert_WithValidDessertCreateDto_ShouldReturnCreatedDessert() {
        // Arrange
        DessertCreateDto dessertCreateDto = new DessertCreateDto();
        dessertCreateDto.setPrice( (float) 10.0);
        dessertCreateDto.setWeight( (float) 100.0);

        Dessert expectedDessert = new Dessert();
        expectedDessert.setId(1L);

        when(dessertMapper.toEntity(dessertCreateDto)).thenReturn(expectedDessert);
        when(dessertRepository.save(expectedDessert)).thenReturn(expectedDessert);
        when(dessertRepository.getById(expectedDessert.getId())).thenReturn(expectedDessert);

        // Act
        Optional<Dessert> actualDessert = dessertService.createDessert(dessertCreateDto);

        // Assert
        assertTrue(actualDessert.isPresent());
        assertEquals(expectedDessert, actualDessert.get());
        verify(dessertRepository, times(1)).save(expectedDessert);
        verify(dessertRepository, times(1)).getById(expectedDessert.getId());
    }

    @Test
    void createDessert_WithInvalidDessertCreateDto_ShouldReturnEmptyOptional() {
        // Arrange
        DessertCreateDto dessertCreateDto = new DessertCreateDto();
        dessertCreateDto.setPrice((float) -10);
        dessertCreateDto.setWeight((float) 100);

        // Act
        Optional<Dessert> actualDessert = dessertService.createDessert(dessertCreateDto);

        // Assert
        assertFalse(actualDessert.isPresent());
        verify(dessertRepository, never()).save(any());
    }

    @Test
    void updateDessert_WithValidIdAndDessertCreateDto_ShouldReturnUpdatedDessert() {
        // Arrange
        Long id = 1L;
        DessertCreateDto dessertCreateDto = new DessertCreateDto();
        dessertCreateDto.setPrice((float) 10);
        dessertCreateDto.setWeight((float) 100);

        Dessert existingDessert = new Dessert();
        existingDessert.setId(id);

        Dessert updatedDessert = new Dessert();
        updatedDessert.setId(id);
        updatedDessert.setPrice((float) 20);
        updatedDessert.setWeight(200);

        when(dessertRepository.findById(id)).thenReturn(Optional.of(existingDessert));
        when(dessertMapper.toEntity(dessertCreateDto)).thenReturn(updatedDessert);
        when(dessertRepository.save(updatedDessert)).thenReturn(updatedDessert);

        // Act
        Optional<Dessert> actualDessert = dessertService.updateDessert(id, dessertCreateDto);

        // Assert
        assertTrue(actualDessert.isPresent());
        assertEquals(updatedDessert, actualDessert.get());
        verify(dessertRepository, times(1)).findById(id);
        verify(dessertRepository, times(1)).save(updatedDessert);
    }

    @Test
    void updateDessert_WithInvalidId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;
        DessertCreateDto dessertCreateDto = new DessertCreateDto();
        dessertCreateDto.setPrice((float) 10);
        dessertCreateDto.setWeight((float) 100);

        when(dessertRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Dessert> actualDessert = dessertService.updateDessert(id, dessertCreateDto);

        // Assert
        assertFalse(actualDessert.isPresent());
        verify(dessertRepository, times(1)).findById(id);
        verify(dessertRepository, never()).save(any());
    }

    @Test
    void updateDessert_WithInvalidDessertCreateDto_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;
        DessertCreateDto dessertCreateDto = new DessertCreateDto();
        dessertCreateDto.setPrice((float) -10);
        dessertCreateDto.setWeight((float) 100);

        // Act
        Optional<Dessert> actualDessert = dessertService.updateDessert(id, dessertCreateDto);

        // Assert
        assertFalse(actualDessert.isPresent());
        verify(dessertRepository, times(1)).findById(any());
        verify(dessertRepository, never()).save(any());
    }

    @Test
    void deleteDessert_WithValidId_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Dessert dessert = new Dessert();
        dessert.setId(id);

        when(dessertRepository.findById(id)).thenReturn(Optional.of(dessert));

        // Act
        boolean result = dessertService.deleteDessert(id);

        // Assert
        assertTrue(result);
        verify(dessertRepository, times(1)).findById(id);
        verify(dessertRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteDessert_WithInvalidId_ShouldReturnFalse() {
        // Arrange
        Long id = 1L;

        when(dessertRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = dessertService.deleteDessert(id);

        // Assert
        assertFalse(result);
        verify(dessertRepository, times(1)).findById(id);
        verify(dessertRepository, never()).deleteById(any());
    }

    @Test
    void findByOrderByPriceAsc_ShouldReturnDessertsOrderedByPriceAsc() {
        // Arrange
        List<Dessert> expectedDesserts = new ArrayList<>();
        expectedDesserts.add(new Dessert());
        expectedDesserts.add(new Dessert());

        when(dessertRepository.findByOrderByPriceAsc()).thenReturn(expectedDesserts);

        // Act
        List<Dessert> actualDesserts = dessertService.findByOrderByPriceAsc();

        // Assert
        assertEquals(expectedDesserts, actualDesserts);
        verify(dessertRepository, times(1)).findByOrderByPriceAsc();
    }

    @Test
    void findByOrderByPriceDesc_ShouldReturnDessertsOrderedByPriceDesc() {
        // Arrange
        List<Dessert> expectedDesserts = new ArrayList<>();
        expectedDesserts.add(new Dessert());
        expectedDesserts.add(new Dessert());

        when(dessertRepository.findByOrderByPriceDesc()).thenReturn(expectedDesserts);

        // Act
        List<Dessert> actualDesserts = dessertService.findByOrderByPriceDesc();

        // Assert
        assertEquals(expectedDesserts, actualDesserts);
        verify(dessertRepository, times(1)).findByOrderByPriceDesc();
    }
}
