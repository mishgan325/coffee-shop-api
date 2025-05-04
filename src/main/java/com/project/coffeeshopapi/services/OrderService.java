package com.project.coffeeshopapi.services;

import com.project.coffeeshopapi.dto.OrderDto;
import com.project.coffeeshopapi.dto.OrderRequest;
import com.project.coffeeshopapi.dto.OrderUpdateDto;
import com.project.coffeeshopapi.dto.UserOrderResponse;
import com.project.coffeeshopapi.models.*;
import com.project.coffeeshopapi.repos.AddonRepository;
import com.project.coffeeshopapi.repos.CoffeeRepository;
import com.project.coffeeshopapi.repos.OrderRepository;
import com.project.coffeeshopapi.repos.UserRepository;
import com.project.coffeeshopapi.utils.DtoMapper;
import com.project.coffeeshopapi.utils.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CoffeeRepository coffeeRepository;
    private final AddonRepository addonRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CoffeeRepository coffeeRepository, AddonRepository addonRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.coffeeRepository = coffeeRepository;
        this.addonRepository = addonRepository;
    }

    public Order createOrder(OrderRequest request) {
        Long telegramId = Long.valueOf(request.getTelegram_token());

        User user = userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setOrderer(user);
        order.setCreatedAt(LocalDateTime.now());

        int total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderRequest.OrderItemRequest itemReq : request.getItems()) {
            Coffee coffee = coffeeRepository.findById(itemReq.getCoffee_id())
                    .orElseThrow(() -> new RuntimeException("Coffee not found"));

            List<Addon> addons = addonRepository.findAllByAddonIdIn(itemReq.getAddon_ids());

            OrderItem orderItem = new OrderItem();
            orderItem.setCoffee(coffee);
            orderItem.setAddons(addons);
            orderItem.setQuantity(1);
            orderItem.setOrder(order);

            int itemTotal = coffee.getPrice() + addons.stream().mapToInt(Addon::getPrice).sum();
            total += itemTotal;

            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }

    public List<UserOrderResponse> getUserOrders(String telegramToken) {
        Long telegramId = Long.valueOf(telegramToken);

        User user = userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findAllByOrderer(user);

        return orders.stream().map(order -> {
            List<UserOrderResponse.Item> items = order.getItems().stream().map(item ->
                    new UserOrderResponse.Item(
                            item.getCoffee().getName(),
                            item.getAddons().stream().map(Addon::getName).collect(Collectors.toList())
                    )
            ).collect(Collectors.toList());

            return new UserOrderResponse(order.getId(), order.getCreatedAt(), items, order.getTotalPrice());
        }).collect(Collectors.toList());
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(DtoMapper::toDto).collect(Collectors.toList());
    }

//    public Order updateOrder(Long id, OrderUpdateDto dto) {
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Заказ не найден"));
//
//        if (dto.getStatus() != null) {
//            order.setStatus(dto.getStatus());
//        }
//
//        return orderRepository.save(order);
//    }

}

