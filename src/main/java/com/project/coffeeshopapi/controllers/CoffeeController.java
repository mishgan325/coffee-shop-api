package com.project.coffeeshopapi.controllers;

import com.project.coffeeshopapi.dto.CoffeeDto;
import com.project.coffeeshopapi.models.Coffee;
import com.project.coffeeshopapi.services.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coffees")
public class CoffeeController {

    private final CoffeeService coffeeService;

    @Autowired
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }


    @PostMapping()
    public ResponseEntity<?> getAllCoffees(@RequestBody Map<String, String> body) {
        try {
            String token = body.get("telegram_token");
            Optional<List<CoffeeDto>> coffees = coffeeService.getAllCoffees(token);
            if (coffees.isPresent()){
                return ResponseEntity.ok(coffees);
            }
            else
                return ResponseEntity.ok(Collections.emptyList());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка при попытке вернуть кофе " + e.getLocalizedMessage());
        }
    }
}
