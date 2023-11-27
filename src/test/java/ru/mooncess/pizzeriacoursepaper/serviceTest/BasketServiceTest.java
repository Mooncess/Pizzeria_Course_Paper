package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.dto.BasketDto;
import ru.mooncess.pizzeriacoursepaper.entities.*;
import ru.mooncess.pizzeriacoursepaper.repositories.*;
import ru.mooncess.pizzeriacoursepaper.repositories.additive.AdditiveRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.pizza.PizzaRepository;
import ru.mooncess.pizzeriacoursepaper.service.BasketService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BasketServiceTest {

    @Mock
    private ProductToPurchaseRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private AdditiveRepository additiveRepository;

    @Mock
    private PizzaRepository pizzaRepository;

    private BasketService basketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        basketService = new BasketService(repository, userRepository, basketRepository, additiveRepository, pizzaRepository);
    }

    @Test
    void purchaseProduct_WithInvalidAdditiveList_ShouldReturnEmptyOptional() {
        // Arrange
        Product sourceProduct = new Product();
        sourceProduct.setId(1L);
        sourceProduct.setTitle("Pizza");
        sourceProduct.setPrice((float) 10);

        Short quantity = 2;

        String username = "testUser";

        Dough dough = new Dough();
        Size size = new Size();

        List<Long> additiveList = Arrays.asList(1L, 2L);

        Basket basket = new Basket();
        basket.setId(1L);
        basket.setBasketItemList(new ArrayList<>());

        User user = new User();
        user.setUsername(username);
        user.setBasket(basket);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(basketRepository.getById(basket.getId())).thenReturn(basket);
        when(additiveRepository.findAllById(additiveList)).thenReturn(Collections.emptyList());

        // Act
        Optional<ProductToPurchase> result = basketService.purchaseProduct(sourceProduct, quantity, username, dough, size, additiveList);

        // Assert
        assertFalse(result.isPresent());
        assertTrue(basket.getBasketItemList().isEmpty());
        verify(userRepository, times(1)).findByUsername(username);
        verify(basketRepository, times(1)).getById(basket.getId());
        verify(additiveRepository, times(1)).findAllById(additiveList);
        verify(pizzaRepository, never()).getById(any());
        verify(repository, never()).findAllByProductIdAndQuantityAndDoughAndSize(any(), any(), any(), any());
        verify(repository, never()).save(any());
    }

    @Test
    void getUserBasketList_WithValidUsername_ShouldReturnBasketDto() {
        // Arrange
        String username = "testUser";

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

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(basketRepository.getById(basket.getId())).thenReturn(basket);

        // Act
        BasketDto result = basketService.getUserBasketList(username);

        // Assert
        assertNotNull(result);
        assertEquals(basketItemList, result.getBasketList());
        assertEquals(30, result.getTotal());
        verify(userRepository, times(1)).findByUsername(username);
        verify(basketRepository, times(1)).getById(basket.getId());
    }

}