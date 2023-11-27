package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.entities.Size;
import ru.mooncess.pizzeriacoursepaper.repositories.SizeRepository;
import ru.mooncess.pizzeriacoursepaper.service.SizeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SizeServiceTest {

    @Mock
    private SizeRepository sizeRepository;

    private SizeService sizeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sizeService = new SizeService(sizeRepository);
    }

    @Test
    void getAllSize_ShouldReturnListOfSizes() {
        // Arrange
        List<Size> sizeList = new ArrayList<>();
        sizeList.add(new Size());
        sizeList.add(new Size());

        when(sizeRepository.findAll()).thenReturn(sizeList);

        // Act
        List<Size> result = sizeService.getAllSize();

        // Assert
        assertEquals(sizeList, result);
        verify(sizeRepository, times(1)).findAll();
    }

    @Test
    void getSizeById_WithValidId_ShouldReturnSizeOptional() {
        // Arrange
        int id = 1;
        Size size = new Size();
        size.setId(id);

        when(sizeRepository.findById(id)).thenReturn(Optional.of(size));

        // Act
        Optional<Size> result = sizeService.getSizeById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(size, result.get());
        verify(sizeRepository, times(1)).findById(id);
    }

    @Test
    void createSize_WithValidName_ShouldReturnSizeOptional() {
        // Arrange
        String name = "Large";
        Size size = new Size();
        size.setName(name);

        when(sizeRepository.save(any(Size.class))).thenReturn(size);

        // Act
        Optional<Size> result = sizeService.createSize(name);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(size, result.get());
        verify(sizeRepository, times(1)).save(any(Size.class));
    }

    @Test
    void updateSize_WithValidIdAndName_ShouldReturnSizeOptional() {
        // Arrange
        int id = 1;
        String name = "Medium";
        Size size = new Size();
        size.setId(id);
        size.setName(name);

        when(sizeRepository.findById(id)).thenReturn(Optional.of(size));
        when(sizeRepository.save(any(Size.class))).thenReturn(size);

        // Act
        Optional<Size> result = sizeService.updateSize(id, name);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(size, result.get());
        verify(sizeRepository, times(1)).findById(id);
        verify(sizeRepository, times(1)).save(any(Size.class));
    }

    @Test
    void updateSize_WithInvalidId_ShouldReturnEmptyOptional() {
        // Arrange
        int id = 1;
        String name = "Medium";

        when(sizeRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Size> result = sizeService.updateSize(id, name);

        // Assert
        assertFalse(result.isPresent());
        verify(sizeRepository, times(1)).findById(id);
        verify(sizeRepository, never()).save(any(Size.class));
    }

    @Test
    void deleteSize_WithValidId_ShouldReturnTrue() {
        // Arrange
        int id = 1;
        Size size = new Size();
        size.setId(id);

        when(sizeRepository.findById(id)).thenReturn(Optional.of(size));

        // Act
        boolean result = sizeService.deleteSize(id);

        // Assert
        assertTrue(result);
        verify(sizeRepository, times(1)).findById(id);
        verify(sizeRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteSize_WithInvalidId_ShouldReturnFalse() {
        // Arrange
        int id = 1;

        when(sizeRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = sizeService.deleteSize(id);

        // Assert
        assertFalse(result);
        verify(sizeRepository, times(1)).findById(id);
        verify(sizeRepository, never()).deleteById(id);
    }
}
