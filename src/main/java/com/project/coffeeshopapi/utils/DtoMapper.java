package com.project.coffeeshopapi.utils;

import com.project.coffeeshopapi.dto.*;
import com.project.coffeeshopapi.models.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class DtoMapper {
    public CoffeeDto convertToCoffeeDto(Coffee coffee){
        return new CoffeeDto(
                coffee.getCoffeeId(),
                coffee.getName(),
                coffee.getDescription(),
                coffee.getImageUrl(),
                coffee.getPrice(),
                coffee.getAddons() == null || coffee.getAddons().isEmpty()
                        ? Collections.emptyList()
                        : coffee.getAddons().stream()
                        .map(this::convertToAddonDto)
                        .collect(Collectors.toList())
        );
    }
    public AddonDto convertToAddonDto(Addon addon){
        return new AddonDto(
                addon.getAddonId(),
                addon.getName(),
                addon.getPrice()
        );
    }

    public static OrderDto toDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setTotalPrice(order.getTotalPrice());

        List<OrderItemDto> itemDtos = order.getItems().stream()
                .map(DtoMapper::toItemDto)
                .collect(Collectors.toList());
        dto.setItems(itemDtos);

        dto.setOrderer(toUserShortDto(order.getOrderer()));

        return dto;
    }

    private static OrderItemDto toItemDto(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setCoffeeName(item.getCoffee().getName());
        dto.setQuantity(item.getQuantity());

        return dto;
    }

    private static UserShortDto toUserShortDto(User user) {
        if (user == null) return null;
        UserShortDto dto = new UserShortDto();
        dto.setTelegramId(user.getTelegramId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        return dto;
    }
}
