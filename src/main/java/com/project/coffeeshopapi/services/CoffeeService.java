package com.project.coffeeshopapi.services;

import com.project.coffeeshopapi.dto.CoffeeDto;
import com.project.coffeeshopapi.dto.CoffeeRequestDto;
import com.project.coffeeshopapi.models.Coffee;
import com.project.coffeeshopapi.repos.CoffeeRepository;
import com.project.coffeeshopapi.repos.UserRepository;
import com.project.coffeeshopapi.utils.DtoMapper;
import com.project.coffeeshopapi.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final DtoMapper dtoMapper;
    private final UserRepository userRepository;
    @Autowired
    public CoffeeService(CoffeeRepository coffeeRepository, DtoMapper dtoMapper, UserRepository userRepository) {
        this.coffeeRepository = coffeeRepository;
        this.dtoMapper = dtoMapper;
        this.userRepository = userRepository;
    }

    public Optional<List<CoffeeDto>> getAllCoffees(String telegramToken) {
        Long telegramId = Long.valueOf(telegramToken);
        userRepository.findByTelegramId(telegramId).orElseThrow(() -> new RuntimeException("Пользователь"));
        return Optional.of(coffeeRepository.findAll().stream().map(dtoMapper::convertToCoffeeDto).collect(Collectors.toList()));
    }
    public Optional<List<CoffeeDto>> getAllCoffeesAdmin(){
        return Optional.of(coffeeRepository.findAll().stream().map(dtoMapper::convertToCoffeeDto).collect(Collectors.toList()));
    }
    public CoffeeDto createCoffee(CoffeeRequestDto dto) {
        Coffee coffee = new Coffee();
        coffee.setName(dto.getName());
        coffee.setDescription(dto.getDescription());
        coffee.setImageUrl(dto.getImageUrl());
        coffee.setPrice(dto.getPrice());
        return dtoMapper.convertToCoffeeDto(coffeeRepository.save(coffee));
    }

    public CoffeeDto updateCoffee(Long id, CoffeeRequestDto dto) {
        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Кофе не найден"));

        if (dto.getName() != null) coffee.setName(dto.getName());
        if (dto.getDescription() != null) coffee.setDescription(dto.getDescription());
        if (dto.getImageUrl() != null) coffee.setImageUrl(dto.getImageUrl());
        if (dto.getPrice() != null) coffee.setPrice(dto.getPrice());

        return dtoMapper.convertToCoffeeDto(coffeeRepository.save(coffee));
    }

    public void deleteCoffee(Long id) {
        if (!coffeeRepository.existsById(id)) {
            throw new NotFoundException("Кофе не найден");
        }
        coffeeRepository.deleteById(id);
    }
}
