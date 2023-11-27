package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.dto.PizzaCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Pizza;
import ru.mooncess.pizzeriacoursepaper.mappers.PizzaMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.additive.AdditiveRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.DoughRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.SizeRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.pizza.PizzaRepository;
import ru.mooncess.pizzeriacoursepaper.service.PizzaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private DoughRepository doughRepository;

    @Mock
    private SizeRepository sizeRepository;

    @Mock
    private AdditiveRepository additiveRepository;

    private PizzaMapper pizzaMapper;

    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pizzaMapper = mock(PizzaMapper.class);
        pizzaService = new PizzaService(pizzaRepository, doughRepository, sizeRepository, additiveRepository);
    }

    @Test
    void getAllPizza_ShouldReturnAllPizzas() {
        // Arrange
        List<Pizza> expectedPizzas = new ArrayList<>();
        expectedPizzas.add(new Pizza());
        expectedPizzas.add(new Pizza());

        when(pizzaRepository.findAll()).thenReturn(expectedPizzas);

        // Act
        List<Pizza> actualPizzas = pizzaService.getAllPizza();

        // Assert
        assertEquals(expectedPizzas, actualPizzas);
        verify(pizzaRepository, times(1)).findAll();
    }

    @Test
    void getPizzaById_WithValidId_ShouldReturnPizza() {
        // Arrange
        Long id = 1L;
        Pizza expectedPizza = new Pizza();
        expectedPizza.setId(id);

        when(pizzaRepository.findById(id)).thenReturn(Optional.of(expectedPizza));

        // Act
        Optional<Pizza> actualPizza = pizzaService.getPizzaById(id);

        // Assert
        assertTrue(actualPizza.isPresent());
        assertEquals(expectedPizza, actualPizza.get());
        verify(pizzaRepository, times(1)).findById(id);
    }

    @Test
    void getPizzaById_WithInvalidId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;

        when(pizzaRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Pizza> actualPizza = pizzaService.getPizzaById(id);

        // Assert
        assertFalse(actualPizza.isPresent());
        verify(pizzaRepository, times(1)).findById(id);
    }

    @Test
    void createPizza_WithValidPizzaCreateDto_ShouldReturnCreatedPizza() {
        // Arrange
        PizzaCreateDto pizzaCreateDto = new PizzaCreateDto();
        pizzaCreateDto.setPrice((float) 10);
        pizzaCreateDto.setAvailableSizes(new ArrayList<>());
        pizzaCreateDto.setAvailableDoughs(new ArrayList<>());
        pizzaCreateDto.setAvailableAdditives(new ArrayList<>());

        Pizza expectedPizzaAfterMapping = new Pizza();
        expectedPizzaAfterMapping.setPrice((float) 10);
        expectedPizzaAfterMapping.setAvailableSizes(new ArrayList<>());
        expectedPizzaAfterMapping.setAvailableDoughs(new ArrayList<>());
        expectedPizzaAfterMapping.setAvailableAdditives(new ArrayList<>());

        Pizza expectedPizza = new Pizza();
        expectedPizza.setId(1L);
        expectedPizza.setPrice((float) 10);
        expectedPizza.setAvailableSizes(new ArrayList<>());
        expectedPizza.setAvailableDoughs(new ArrayList<>());
        expectedPizza.setAvailableAdditives(new ArrayList<>());

        when(pizzaMapper.pizzaCreateDtoToEntity(pizzaCreateDto)).thenReturn(expectedPizzaAfterMapping);
        when(sizeRepository.findAllById(anyList())).thenReturn(new ArrayList<>());
        when(doughRepository.findAllById(anyList())).thenReturn(new ArrayList<>());
        when(additiveRepository.findAllById(anyList())).thenReturn(new ArrayList<>());
        when(pizzaRepository.save(expectedPizza)).thenReturn(expectedPizza);

        // Act
        Optional<Pizza> actualPizza = pizzaService.createPizza(pizzaCreateDto);

        // Assert
        assertTrue(actualPizza.isPresent());
        assertEquals(expectedPizza, actualPizza.get());
        verify(pizzaRepository, times(1)).save(expectedPizza);
    }

    @Test
    void createPizza_WithInvalidPizzaCreateDto_ShouldReturnEmptyOptional() {
        // Arrange
        PizzaCreateDto pizzaCreateDto = new PizzaCreateDto();
        pizzaCreateDto.setPrice((float) -10);

        // Act
        Optional<Pizza> actualPizza = pizzaService.createPizza(pizzaCreateDto);

        // Assert
        assertFalse(actualPizza.isPresent());
        verify(pizzaRepository, never()).save(any());
    }

    @Test
    void updatePizza_WithValidIdAndPizzaCreateDto_ShouldReturnUpdatedPizza() {
        // Arrange
        Long id = 1L;
        PizzaCreateDto pizzaCreateDto = new PizzaCreateDto();
        pizzaCreateDto.setPrice((float) 20);
        pizzaCreateDto.setAvailableSizes(new ArrayList<>());
        pizzaCreateDto.setAvailableDoughs(new ArrayList<>());
        pizzaCreateDto.setAvailableAdditives(new ArrayList<>());

        Pizza existingPizza = new Pizza();
        existingPizza.setId(id);
        existingPizza.setAvailableSizes(new ArrayList<>());
        existingPizza.setAvailableDoughs(new ArrayList<>());
        existingPizza.setAvailableAdditives(new ArrayList<>());

        Pizza updatedPizza = new Pizza();
        updatedPizza.setPrice((float) 20);
        updatedPizza.setAvailableSizes(new ArrayList<>());
        updatedPizza.setAvailableDoughs(new ArrayList<>());
        updatedPizza.setAvailableAdditives(new ArrayList<>());

        when(pizzaRepository.findById(id)).thenReturn(Optional.of(existingPizza));
        when(pizzaMapper.pizzaCreateDtoToEntity(pizzaCreateDto)).thenReturn(updatedPizza);
        when(pizzaRepository.save(updatedPizza)).thenReturn(updatedPizza);

        // Act
        Optional<Pizza> actualPizza = pizzaService.updatePizza(id, pizzaCreateDto);

        // Assert
        assertTrue(actualPizza.isPresent());
        assertEquals(updatedPizza, actualPizza.get());
        verify(pizzaRepository, times(1)).findById(id);
        verify(pizzaRepository, times(1)).save(updatedPizza);
    }

    @Test
    void updatePizza_WithInvalidId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;
        PizzaCreateDto pizzaCreateDto = new PizzaCreateDto();
        pizzaCreateDto.setPrice((float) 10);

        when(pizzaRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Pizza> actualPizza = pizzaService.updatePizza(id, pizzaCreateDto);

        // Assert
        assertFalse(actualPizza.isPresent());
        verify(pizzaRepository, times(1)).findById(id);
        verify(pizzaRepository, never()).save(any());
    }

    @Test
    void updatePizza_WithInvalidPizzaCreateDto_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;
        PizzaCreateDto pizzaCreateDto = new PizzaCreateDto();
        pizzaCreateDto.setPrice((float) -10);

        // Act
        Optional<Pizza> actualPizza = pizzaService.updatePizza(id, pizzaCreateDto);

        // Assert
        assertFalse(actualPizza.isPresent());
        verify(pizzaRepository, times(1)).findById(any());
        verify(pizzaRepository, never()).save(any());
    }

    @Test
    void deletePizza_WithValidId_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Pizza pizza = new Pizza();
        pizza.setId(id);

        when(pizzaRepository.findById(id)).thenReturn(Optional.of(pizza));

        // Act
        boolean result = pizzaService.deletePizza(id);

        // Assert
        assertTrue(result);
        verify(pizzaRepository, times(1)).findById(id);
        verify(pizzaRepository, times(1)).deleteById(id);
    }

    @Test
    void deletePizza_WithInvalidId_ShouldReturnFalse() {
        // Arrange
        Long id = 1L;

        when(pizzaRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = pizzaService.deletePizza(id);

        // Assert
        assertFalse(result);
        verify(pizzaRepository, times(1)).findById(id);
        verify(pizzaRepository, never()).deleteById(any());
    }

    @Test
    void findByOrderByPriceAsc_ShouldReturnPizzasOrderedByPriceAsc() {
        // Arrange
        List<Pizza> expectedPizzas = new ArrayList<>();
        expectedPizzas.add(new Pizza());
        expectedPizzas.add(new Pizza());

        when(pizzaRepository.findByOrderByPriceAsc()).thenReturn(expectedPizzas);

        // Act
        List<Pizza> actualPizzas = pizzaService.findByOrderByPriceAsc();

        // Assert
        assertEquals(expectedPizzas, actualPizzas);
        verify(pizzaRepository, times(1)).findByOrderByPriceAsc();
    }

    @Test
    void findByOrderByPriceDesc_ShouldReturnPizzasOrderedByPriceDesc() {
        // Arrange
        List<Pizza> expectedPizzas = new ArrayList<>();
        expectedPizzas.add(new Pizza());
        expectedPizzas.add(new Pizza());

        when(pizzaRepository.findByOrderByPriceDesc()).thenReturn(expectedPizzas);

        // Act
        List<Pizza> actualPizzas = pizzaService.findByOrderByPriceDesc();

        // Assert
        assertEquals(expectedPizzas, actualPizzas);
        verify(pizzaRepository, times(1)).findByOrderByPriceDesc();
    }
}
