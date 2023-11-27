package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.dto.ClientOrderDto;
import ru.mooncess.pizzeriacoursepaper.entities.*;
import ru.mooncess.pizzeriacoursepaper.mappers.OrderMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.BasketRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.OrderRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.OrderStatusRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.UserRepository;
import ru.mooncess.pizzeriacoursepaper.service.OrderService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderStatusRepository orderStatusRepository;

    private OrderMapper orderMapper;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderMapper = mock(OrderMapper.class);
        orderService = new OrderService(userRepository, basketRepository, orderRepository, orderStatusRepository);
    }

    @Test
    void createOrder_WithValidUsernameAndAddressAndNonEmptyBasket_ShouldReturnClientOrderDto() {
        // Arrange
        String username = "testUser";
        String address = "123 Test Street";

        Basket basket = new Basket();
        basket.setId(1L);
        List<ProductToPurchase> basketItemList = new ArrayList<>();
        ProductToPurchase product1 = new ProductToPurchase();
        product1.setPrice(10);
        ProductToPurchase product2 = new ProductToPurchase();
        product2.setPrice(20);
        basketItemList.add(product1);
        basketItemList.add(product2);
        basket.setBasketItemList(basketItemList);

        User user = new User();
        user.setUsername(username);
        user.setBasket(basket);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(1);

        Order order = new Order();
        order.setAddress(address);
        order.setBuyer(user);
        order.setStatus(orderStatus);
        order.setTotal((float) 30);
        order.setOrderItemList(basketItemList);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(orderStatusRepository.getById(1)).thenReturn(orderStatus);
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Optional<ClientOrderDto> result = orderService.createOrder(username, address);

        // Assert
        assertTrue(result.isPresent());
        assertTrue(basket.getBasketItemList().isEmpty());
        verify(userRepository, times(2)).findByUsername(username);
        // verify(basketRepository, times(1)).getById(basket.getId());
        verify(orderStatusRepository, times(1)).getById(1);
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void createOrder_WithEmptyBasket_ShouldReturnEmptyOptional() {
        // Arrange
        String username = "testUser";
        String address = "123 Test Street";

        Basket basket = new Basket();
        basket.setId(1L);
        basket.setBasketItemList(Collections.emptyList());

        User user = new User();
        user.setUsername(username);
        user.setBasket(basket);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        Optional<ClientOrderDto> result = orderService.createOrder(username, address);

        // Assert
        assertFalse(result.isPresent());
        assertTrue(basket.getBasketItemList().isEmpty());
        verify(userRepository, times(1)).findByUsername(username);
        verify(basketRepository, never()).getById(any());
        verify(orderStatusRepository, never()).getById(any());
        verify(orderRepository, never()).save(any());
    }
}
