package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.entities.OrderStatus;
import ru.mooncess.pizzeriacoursepaper.repositories.OrderStatusRepository;
import ru.mooncess.pizzeriacoursepaper.service.OrderStatusService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderStatusServiceTest {

    @Mock
    private OrderStatusRepository orderStatusRepository;

    private OrderStatusService orderStatusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderStatusService = new OrderStatusService(orderStatusRepository);
    }

    @Test
    void getAllOrderStatus_ShouldReturnListOfOrderStatus() {
        // Arrange
        List<OrderStatus> orderStatusList = new ArrayList<>();
        OrderStatus orderStatus1 = new OrderStatus();
        orderStatus1.setId(1);
        OrderStatus orderStatus2 = new OrderStatus();
        orderStatus2.setId(2);
        orderStatusList.add(orderStatus1);
        orderStatusList.add(orderStatus2);

        when(orderStatusRepository.findAll()).thenReturn(orderStatusList);

        // Act
        List<OrderStatus> result = orderStatusService.getAllOrderStatus();

        // Assert
        assertEquals(orderStatusList.size(), result.size());
        for (int i = 0; i < orderStatusList.size(); i++) {
            assertEquals(orderStatusList.get(i), result.get(i));
        }
        verify(orderStatusRepository, times(1)).findAll();
    }

    @Test
    void createOrderStatus_WithValidName_ShouldReturnOrderStatusOptional() {
        // Arrange
        String name = "New Status";

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setName(name);

        when(orderStatusRepository.save(orderStatus)).thenReturn(orderStatus);

        // Act
        Optional<OrderStatus> result = orderStatusService.createOrderStatus(name);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(orderStatus, result.get());
        verify(orderStatusRepository, times(1)).save(orderStatus);
    }

    @Test
    void updateOrderStatus_WithExistingIdAndValidName_ShouldReturnOrderStatusOptional() {
        // Arrange
        Integer id = 1;
        String name = "Updated Status";

        OrderStatus existingOrderStatus = new OrderStatus();
        existingOrderStatus.setId(id);

        OrderStatus updatedOrderStatus = new OrderStatus();
        updatedOrderStatus.setId(id);
        updatedOrderStatus.setName(name);

        when(orderStatusRepository.findById(id)).thenReturn(Optional.of(existingOrderStatus));
        when(orderStatusRepository.save(updatedOrderStatus)).thenReturn(updatedOrderStatus);

        // Act
        Optional<OrderStatus> result = orderStatusService.updateOrderStatus(id, name);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(updatedOrderStatus, result.get());
        verify(orderStatusRepository, times(1)).findById(id);
        verify(orderStatusRepository, times(1)).save(updatedOrderStatus);
    }

    @Test
    void updateOrderStatus_WithNonExistingId_ShouldReturnEmptyOptional() {
        // Arrange
        Integer id = 1;
        String name = "Updated Status";

        when(orderStatusRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<OrderStatus> result = orderStatusService.updateOrderStatus(id, name);

        // Assert
        assertFalse(result.isPresent());
        verify(orderStatusRepository, times(1)).findById(id);
        verify(orderStatusRepository, never()).save(any());
    }

    @Test
    void deleteOrderStatus_WithExistingId_ShouldReturnTrue() {
        // Arrange
        Integer id = 1;
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(id);

        when(orderStatusRepository.findById(id)).thenReturn(Optional.of(orderStatus));

        // Act
        boolean result = orderStatusService.deleteOrderStatus(id);

        // Assert
        assertTrue(result);
        verify(orderStatusRepository, times(1)).findById(id);
        verify(orderStatusRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteOrderStatus_WithNonExistingId_ShouldReturnFalse() {
        // Arrange
        Integer id = 1;

        when(orderStatusRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = orderStatusService.deleteOrderStatus(id);

        // Assert
        assertFalse(result);
        verify(orderStatusRepository, times(1)).findById(id);
        verify(orderStatusRepository, never()).deleteById(any());
    }
}
