package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.entities.Drink;
import ru.mooncess.pizzeriacoursepaper.mappers.DrinkMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.drink.DrinkRepository;
import ru.mooncess.pizzeriacoursepaper.service.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DrinkServiceTest {

    @Mock
    private DrinkRepository drinkRepository;

    @Mock
    private DrinkMapper drinkMapper;

    private DrinkService drinkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        drinkService = new DrinkService(drinkRepository, drinkMapper);
    }

    @Test
    void getAllDrink_ShouldReturnListOfDrink() {
        // Arrange
        List<Drink> drinkList = new ArrayList<>();
        Drink drink1 = new Drink();
        drink1.setId(1L);
        Drink drink2 = new Drink();
        drink2.setId(2L);
        drinkList.add(drink1);
        drinkList.add(drink2);

        when(drinkRepository.findAll()).thenReturn(drinkList);

        // Act
        List<Drink> result = drinkService.getAllDrink();

        // Assert
        assertEquals(drinkList.size(), result.size());
        for (int i = 0; i < drinkList.size(); i++) {
            assertEquals(drinkList.get(i), result.get(i));
        }
        verify(drinkRepository, times(1)).findAll();
    }

    @Test
    void getDrinkById_WithExistingId_ShouldReturnDrinkOptional() {
        // Arrange
        Long id = 1L;
        Drink drink = new Drink();
        drink.setId(id);

        when(drinkRepository.findById(id)).thenReturn(Optional.of(drink));

        // Act
        Optional<Drink> result = drinkService.getDrinkById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(drink, result.get());
        verify(drinkRepository, times(1)).findById(id);
    }

    @Test
    void getDrinkById_WithNonExistingId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;

        when(drinkRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Drink> result = drinkService.getDrinkById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(drinkRepository, times(1)).findById(id);
    }

    // Другие тесты для остальных методов класса DrinkService

    @Test
    void deleteDrink_WithExistingId_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Drink drink = new Drink();
        drink.setId(id);

        when(drinkRepository.findById(id)).thenReturn(Optional.of(drink));

        // Act
        boolean result = drinkService.deleteDrink(id);

        // Assert
        assertTrue(result);
        verify(drinkRepository, times(1)).findById(id);
        verify(drinkRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteDrink_WithNonExistingId_ShouldReturnFalse() {
        // Arrange
        Long id = 1L;

        when(drinkRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = drinkService.deleteDrink(id);

        // Assert
        assertFalse(result);
        verify(drinkRepository, times(1)).findById(id);
        verify(drinkRepository, never()).deleteById(any());
    }
}