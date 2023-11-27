package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.dto.SnackCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Snack;
import ru.mooncess.pizzeriacoursepaper.mappers.SnackMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.snack.SnackRepository;
import ru.mooncess.pizzeriacoursepaper.service.SnackService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SnackServiceTest {

    @Mock
    private SnackRepository snackRepository;
    @Mock
    private SnackMapper snackMapper;

    private SnackService snackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        snackService = new SnackService(snackRepository, snackMapper);
    }

    @Test
    void getAllSnack_ShouldReturnAllSnacks() {
        // Arrange
        List<Snack> expectedSnacks = new ArrayList<>();
        expectedSnacks.add(new Snack());
        expectedSnacks.add(new Snack());

        when(snackRepository.findAll()).thenReturn(expectedSnacks);

        // Act
        List<Snack> actualSnacks = snackService.getAllSnack();

        // Assert
        assertEquals(expectedSnacks, actualSnacks);
        verify(snackRepository, times(1)).findAll();
    }

    @Test
    void getSnackById_WithValidId_ShouldReturnSnack() {
        // Arrange
        Long id = 1L;
        Snack expectedSnack = new Snack();
        expectedSnack.setId(id);

        when(snackRepository.findById(id)).thenReturn(Optional.of(expectedSnack));

        // Act
        Optional<Snack> actualSnack = snackService.getSnackById(id);

        // Assert
        assertTrue(actualSnack.isPresent());
        assertEquals(expectedSnack, actualSnack.get());
        verify(snackRepository, times(1)).findById(id);
    }

    @Test
    void getSnackById_WithInvalidId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;

        when(snackRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Snack> actualSnack = snackService.getSnackById(id);

        // Assert
        assertFalse(actualSnack.isPresent());
        verify(snackRepository, times(1)).findById(id);
    }

    @Test
    void createSnack_WithValidSnackCreateDto_ShouldReturnCreatedSnack() {
        // Arrange
        SnackCreateDto snackCreateDto = new SnackCreateDto();
        snackCreateDto.setPrice((float) 10);
        snackCreateDto.setWeight((float) 200);

        Snack expectedSnack = new Snack();
        expectedSnack.setId(1L);

        when(snackMapper.toEntity(snackCreateDto)).thenReturn(expectedSnack);
        when(snackRepository.getById(any())).thenReturn(expectedSnack);
        when(snackRepository.save(expectedSnack)).thenReturn(expectedSnack);

        // Act
        Optional<Snack> actualSnack = snackService.createSnack(snackCreateDto);

        // Assert
        assertTrue(actualSnack.isPresent());
        assertEquals(expectedSnack, actualSnack.get());
        verify(snackRepository, times(1)).save(expectedSnack);
    }

    @Test
    void createSnack_WithInvalidSnackCreateDto_ShouldReturnEmptyOptional() {
        // Arrange
        SnackCreateDto snackCreateDto = new SnackCreateDto();
        snackCreateDto.setPrice((float) -10);
        snackCreateDto.setWeight((float) 200);

        // Act
        Optional<Snack> actualSnack = snackService.createSnack(snackCreateDto);

        // Assert
        assertFalse(actualSnack.isPresent());
        verify(snackRepository, never()).save(any());
    }

    @Test
    void updateSnack_WithValidIdAndSnackCreateDto_ShouldReturnUpdatedSnack() {
        // Arrange
        Long id = 1L;
        SnackCreateDto snackCreateDto = new SnackCreateDto();
        snackCreateDto.setPrice((float) 10);
        snackCreateDto.setWeight((float) 200);

        Snack existingSnack = new Snack();
        existingSnack.setId(id);

        Snack updatedSnack = new Snack();
        updatedSnack.setId(id);
        updatedSnack.setPrice((float) 20);
        updatedSnack.setWeight((float) 300);

        when(snackRepository.findById(id)).thenReturn(Optional.of(existingSnack));
        when(snackMapper.toEntity(snackCreateDto)).thenReturn(updatedSnack);
        when(snackRepository.save(updatedSnack)).thenReturn(updatedSnack);

        // Act
        Optional<Snack> actualSnack = snackService.updateSnack(id, snackCreateDto);

        // Assert
        assertTrue(actualSnack.isPresent());
        assertEquals(updatedSnack, actualSnack.get());
        verify(snackRepository, times(1)).findById(id);
        verify(snackRepository, times(1)).save(updatedSnack);
    }

    @Test
    void updateSnack_WithInvalidId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;
        SnackCreateDto snackCreateDto = new SnackCreateDto();
        snackCreateDto.setPrice((float) 10);
        snackCreateDto.setWeight((float) 200);

        when(snackRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Snack> actualSnack = snackService.updateSnack(id, snackCreateDto);

        // Assert
        assertFalse(actualSnack.isPresent());
        verify(snackRepository, times(1)).findById(id);
        verify(snackRepository, never()).save(any());
    }

    @Test
    void updateSnack_WithInvalidSnackCreateDto_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;
        SnackCreateDto snackCreateDto = new SnackCreateDto();
        snackCreateDto.setPrice((float) -10);
        snackCreateDto.setWeight((float) 200);

        // Act
        Optional<Snack> actualSnack = snackService.updateSnack(id, snackCreateDto);

        // Assert
        assertFalse(actualSnack.isPresent());
        verify(snackRepository, times(1)).findById(any());
        verify(snackRepository, never()).save(any());
    }

    @Test
    void deleteSnack_WithValidId_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Snack snack = new Snack();
        snack.setId(id);

        when(snackRepository.findById(id)).thenReturn(Optional.of(snack));

        // Act
        boolean result = snackService.deleteSnack(id);

        // Assert
        assertTrue(result);
        verify(snackRepository, times(1)).findById(id);
        verify(snackRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteSnack_WithInvalidId_ShouldReturnFalse() {
        // Arrange
        Long id = 1L;

        when(snackRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = snackService.deleteSnack(id);

        // Assert
        assertFalse(result);
        verify(snackRepository, times(1)).findById(id);
        verify(snackRepository, never()).deleteById(any());
    }

    @Test
    void findByOrderByPriceAsc_ShouldReturnSnacksOrderedByPriceAsc() {
        // Arrange
        List<Snack> expectedSnacks = new ArrayList<>();
        expectedSnacks.add(new Snack());
        expectedSnacks.add(new Snack());

        when(snackRepository.findByOrderByPriceAsc()).thenReturn(expectedSnacks);

        // Act
        List<Snack> actualSnacks = snackService.findByOrderByPriceAsc();

        // Assert
        assertEquals(expectedSnacks, actualSnacks);
        verify(snackRepository, times(1)).findByOrderByPriceAsc();
    }

    @Test
    void findByOrderByPriceDesc_ShouldReturnSnacksOrderedByPriceDesc() {
        // Arrange
        List<Snack> expectedSnacks = new ArrayList<>();
        expectedSnacks.add(new Snack());
        expectedSnacks.add(new Snack());

        when(snackRepository.findByOrderByPriceDesc()).thenReturn(expectedSnacks);

        // Act
        List<Snack> actualSnacks = snackService.findByOrderByPriceDesc();

        // Assert
        assertEquals(expectedSnacks, actualSnacks);
        verify(snackRepository, times(1)).findByOrderByPriceDesc();
    }
}
