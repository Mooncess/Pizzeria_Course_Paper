package ru.mooncess.pizzeriacoursepaper.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.mooncess.pizzeriacoursepaper.dto.ClientOrderDto;
import ru.mooncess.pizzeriacoursepaper.dto.OrderDto;
import ru.mooncess.pizzeriacoursepaper.entities.*;
import ru.mooncess.pizzeriacoursepaper.mappers.OrderMapper;
import ru.mooncess.pizzeriacoursepaper.repositories.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final BasketRepository basketRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
    public Optional<ClientOrderDto> createOrder(String username, String address) {
        Basket basket = userRepository.findByUsername(username).get().getBasket();
        try {
            if (basket.getBasketItemList().isEmpty()) {
                return Optional.empty();
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Order order = new Order();
            order.setAddress(address);
            order.setCreationDate(LocalDateTime.now().format(formatter));
            order.setBuyer(userRepository.findByUsername(username).get());
            OrderStatus orderStatus =orderStatusRepository.getById(1);
            order.setStatus(orderStatus);
            List<ProductToPurchase> list = new ArrayList<>();
            float total = 0;
            for (ProductToPurchase i : basket.getBasketItemList()) {
                list.add(i);
                total += i.getPrice();
            }
            order.setTotal(total);
            order.setOrderItemList(list);
            orderRepository.save(order);
            basket.setBasketItemList(Collections.emptyList());
            basketRepository.save(basket);
            return Optional.of(orderMapper.entityToClientDto(order));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<ClientOrderDto> getAllClientOrder(String username) {
        List<Order> orderList = userRepository.findByUsername(username).get().getOrderList();
        List<ClientOrderDto> list = new ArrayList<>();
        for (Order i : orderList) {
            list.add(orderMapper.entityToClientDto(i));
        }
        return list;
    }

    public Optional<ClientOrderDto> getClientOrderById(String username, long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        long userId = userRepository.findByUsername(username).get().getId();
        if (optionalOrder.isPresent() && optionalOrder.get().getBuyer().getId() == userId) {
            return Optional.of(orderMapper.entityToClientDto(optionalOrder.get()));
        }
        return Optional.empty();
    }

    public List<OrderDto> getAllOrder() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderDto> list = new ArrayList<>();
        for (Order i : orderList) {
            list.add(orderMapper.entityToDto(i));
        }
        return list;
    }

    public Optional<OrderDto> getOrderById(long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.map(orderMapper::entityToDto);
    }

    public List<OrderDto> getOrderByStatus(int orderStatusId) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findById(orderStatusId);
        List<OrderDto> list = new ArrayList<>();
        if (optionalOrderStatus.isPresent()) {
            List<Order> optionalOrder = orderRepository.findAllByStatus(optionalOrderStatus.get());
            for (Order i : optionalOrder) {
                list.add(orderMapper.entityToDto(i));
            }
        }
        return list;
    }

    public Optional<ClientOrderDto> updateOrderStatusOfOrder(long orderId, int orderStatusId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findById(orderStatusId);
        if (optionalOrder.isPresent() && optionalOrderStatus.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(optionalOrderStatus.get());
            orderRepository.save(order);
            return Optional.of(orderMapper.entityToClientDto(order));
        }
        return Optional.empty();
    }
}
