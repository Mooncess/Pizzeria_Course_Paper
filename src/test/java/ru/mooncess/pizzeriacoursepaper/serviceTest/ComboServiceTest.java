package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.dto.ComboCreateDto;
import ru.mooncess.pizzeriacoursepaper.entities.Combo;
import ru.mooncess.pizzeriacoursepaper.entities.Dough;
import ru.mooncess.pizzeriacoursepaper.entities.Size;
import ru.mooncess.pizzeriacoursepaper.mappers.ComboMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.DoughRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.SizeRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.combo.ComboRepository;
import ru.mooncess.pizzeriacoursepaper.service.ComboService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComboServiceTest {

    @Mock
    private ComboRepository comboRepository;

    @Mock
    private DoughRepository doughRepository;

    @Mock
    private SizeRepository sizeRepository;

    private ComboMapper comboMapper;

    private ComboService comboService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        comboMapper = mock(ComboMapper.class);
        comboService = new ComboService(comboRepository, doughRepository, sizeRepository);
    }

    @Test
    void getAllCombo_ShouldReturnAllCombos() {
        // Arrange
        List<Combo> expectedCombos = new ArrayList<>();
        expectedCombos.add(new Combo());
        expectedCombos.add(new Combo());

        when(comboRepository.findAll()).thenReturn(expectedCombos);

        // Act
        List<Combo> actualCombos = comboService.getAllCombo();

        // Assert
        assertEquals(expectedCombos, actualCombos);
        verify(comboRepository, times(1)).findAll();
    }

    @Test
    void getComboById_WithValidId_ShouldReturnCombo() {
        // Arrange
        Long id = 1L;
        Combo expectedCombo = new Combo();
        expectedCombo.setId(id);

        when(comboRepository.findById(id)).thenReturn(Optional.of(expectedCombo));

        // Act
        Optional<Combo> actualCombo = comboService.getComboById(id);

        // Assert
        assertTrue(actualCombo.isPresent());
        assertEquals(expectedCombo, actualCombo.get());
        verify(comboRepository, times(1)).findById(id);
    }

    @Test
    void getComboById_WithInvalidId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;

        when(comboRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Combo> actualCombo = comboService.getComboById(id);

        // Assert
        assertFalse(actualCombo.isPresent());
        verify(comboRepository, times(1)).findById(id);
    }

    @Test
    void createCombo_WithValidComboCreateDto_ShouldReturnCreatedCombo() {
        // Arrange
        Size size = new Size();
        size.setId(2); size.setName("testSize");

        Dough dough = new Dough();
        dough.setId(3); dough.setName("testDough");

        Size sizeAfterMapping = new Size();
        size.setId(2);

        Dough doughAfterMapping = new Dough();
        dough.setId(3);

        ComboCreateDto comboCreateDto = new ComboCreateDto();
        comboCreateDto.setPrice((float) 10);
        comboCreateDto.setAvailableDough(3);
        comboCreateDto.setAvailableSize(2);

        Combo comboAfterMapping = new Combo();
        comboAfterMapping.setPrice((float) 10);
        comboAfterMapping.setAvailableDough(doughAfterMapping);
        comboAfterMapping.setAvailableSize(sizeAfterMapping);

        Combo expectedCombo = new Combo();
        expectedCombo.setId(1L);
        expectedCombo.setPrice((float) 10);
        expectedCombo.setAvailableDough(dough);
        expectedCombo.setAvailableSize(size);

        when(comboMapper.comboCreateDtoToEntity(comboCreateDto)).thenReturn(comboAfterMapping);
        when(sizeRepository.findById(any())).thenReturn(Optional.of(size));
        when(doughRepository.findById(any())).thenReturn(Optional.of(dough));
        when(comboRepository.save(expectedCombo)).thenReturn(expectedCombo);

        // Act
        Optional<Combo> actualCombo = comboService.createCombo(comboCreateDto);

        // Assert
        assertTrue(actualCombo.isPresent());
        assertEquals(expectedCombo, actualCombo.get());
        verify(comboRepository, times(1)).save(expectedCombo);
    }

    @Test
    void createCombo_WithInvalidComboCreateDto_ShouldReturnEmptyOptional() {
        // Arrange
        ComboCreateDto comboCreateDto = new ComboCreateDto();
        comboCreateDto.setPrice((float) -10);

        // Act
        Optional<Combo> actualCombo = comboService.createCombo(comboCreateDto);

        // Assert
        assertFalse(actualCombo.isPresent());
        verify(comboRepository, never()).save(any());
    }

    @Test
    void updateCombo_WithValidIdAndComboCreateDto_ShouldReturnUpdatedCombo() {
        // Arrange
        Size size = new Size();
        size.setId(2); size.setName("testSize");

        Dough dough = new Dough();
        dough.setId(3); dough.setName("testDough");

        Size sizeAfterMapping = new Size();
        sizeAfterMapping.setId(2);

        Dough doughAfterMapping = new Dough();
        doughAfterMapping.setId(3);

        Long id = 1L;
        ComboCreateDto comboCreateDto = new ComboCreateDto();
        comboCreateDto.setPrice((float) 20);
        comboCreateDto.setAvailableSize(2);
        comboCreateDto.setAvailableDough(3);

        Combo existingCombo = new Combo();
        existingCombo.setAvailableSize(sizeAfterMapping);
        existingCombo.setAvailableDough(doughAfterMapping);

        Combo updatedCombo = new Combo();
        updatedCombo.setId(id);
        updatedCombo.setPrice((float) 20);
        updatedCombo.setAvailableDough(dough);
        updatedCombo.setAvailableSize(size);

        when(comboRepository.findById(id)).thenReturn(Optional.of(existingCombo));
        when(comboMapper.comboCreateDtoToEntity(comboCreateDto)).thenReturn(updatedCombo);
        when(comboRepository.save(existingCombo)).thenReturn(updatedCombo);

        // Act
        Optional<Combo> actualCombo = comboService.updateCombo(id, comboCreateDto);

        // Assert
        assertTrue(actualCombo.isPresent());
        assertEquals(updatedCombo, actualCombo.get());
        verify(comboRepository, times(1)).findById(id);
        verify(comboRepository, times(1)).save(existingCombo);
    }

    @Test
    void updateCombo_WithInvalidId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;
        ComboCreateDto comboCreateDto = new ComboCreateDto();
        comboCreateDto.setPrice((float) 10);

        when(comboRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Combo> actualCombo = comboService.updateCombo(id, comboCreateDto);

        // Assert
        assertFalse(actualCombo.isPresent());
        verify(comboRepository, times(1)).findById(id);
        verify(comboRepository, never()).save(any());
    }

    @Test
    void updateCombo_WithInvalidComboCreateDto_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;
        ComboCreateDto comboCreateDto = new ComboCreateDto();
        comboCreateDto.setPrice((float) -10);

        // Act
        Optional<Combo> actualCombo = comboService.updateCombo(id, comboCreateDto);

        // Assert
        assertFalse(actualCombo.isPresent());
        verify(comboRepository, times(1)).findById(any());
        verify(comboRepository, never()).save(any());
    }

    @Test
    void deleteCombo_WithValidId_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Combo combo = new Combo();
        combo.setId(id);

        when(comboRepository.findById(id)).thenReturn(Optional.of(combo));

        // Act
        boolean result = comboService.deleteCombo(id);

        // Assert
        assertTrue(result);
        verify(comboRepository, times(1)).findById(id);
        verify(comboRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteCombo_WithInvalidId_ShouldReturnFalse() {
        // Arrange
        Long id = 1L;

        when(comboRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = comboService.deleteCombo(id);

        // Assert
        assertFalse(result);
        verify(comboRepository, times(1)).findById(id);
        verify(comboRepository, never()).deleteById(any());
    }

    @Test
    void findByOrderByPriceAsc_ShouldReturnCombosOrderedByPriceAsc() {
        // Arrange
        List<Combo> expectedCombos = new ArrayList<>();
        expectedCombos.add(new Combo());
        expectedCombos.add(new Combo());

        when(comboRepository.findByOrderByPriceAsc()).thenReturn(expectedCombos);

        // Act
        List<Combo> actualCombos = comboService.findByOrderByPriceAsc();

        // Assert
        assertEquals(expectedCombos, actualCombos);
        verify(comboRepository, times(1)).findByOrderByPriceAsc();
    }

    @Test
    void findByOrderByPriceDesc_ShouldReturnCombosOrderedByPriceDesc() {
        // Arrange
        List<Combo> expectedCombos = new ArrayList<>();
        expectedCombos.add(new Combo());
        expectedCombos.add(new Combo());

        when(comboRepository.findByOrderByPriceDesc()).thenReturn(expectedCombos);

        // Act
        List<Combo> actualCombos = comboService.findByOrderByPriceDesc();

        // Assert
        assertEquals(expectedCombos, actualCombos);
        verify(comboRepository, times(1)).findByOrderByPriceDesc();
    }
}
