package com.project.coffeeshopapi.controllers;

import com.project.coffeeshopapi.dto.AddonDto;
import com.project.coffeeshopapi.dto.AddonDtoRequest;
import com.project.coffeeshopapi.dto.CoffeeDto;
import com.project.coffeeshopapi.dto.CoffeeRequestDto;
import com.project.coffeeshopapi.models.User;
import com.project.coffeeshopapi.services.AddonService;
import com.project.coffeeshopapi.services.CoffeeService;
import com.project.coffeeshopapi.services.OrderService;
import com.project.coffeeshopapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CoffeeService coffeeService;
    private final AddonService addonService;
    private final OrderService orderService;

    public AdminController(UserService userService,
                           CoffeeService coffeeService,
                           AddonService addonService,
                           OrderService orderService) {
        this.userService = userService;
        this.coffeeService = coffeeService;
        this.addonService = addonService;
        this.orderService = orderService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/coffees")
    public ResponseEntity<?> createCoffee(@RequestBody CoffeeRequestDto coffeeDto) {
        try {
            return ResponseEntity.ok(coffeeService.createCoffee(coffeeDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/coffees")
    public ResponseEntity<?> getAllCoffees() {
        try {
            Optional<List<CoffeeDto>> coffees = coffeeService.getAllCoffeesAdmin();
            if (coffees.isPresent()) {
                return ResponseEntity.ok(coffees);
            } else
                return ResponseEntity.ok(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при попытке вернуть кофе " + e.getLocalizedMessage());
        }
    }

    @PutMapping("/coffees/{id}")
    public ResponseEntity<?> updateCoffee(@PathVariable Long id, @RequestBody CoffeeRequestDto coffeeDto) {
        try {
            return ResponseEntity.ok(coffeeService.updateCoffee(id, coffeeDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }

    }

    @DeleteMapping("/coffees/{id}")
    public ResponseEntity<?> deleteCoffee(@PathVariable Long id) {
        try {
            coffeeService.deleteCoffee(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }

    }

    @PostMapping("/addons")
    public ResponseEntity<?> createAddon(@RequestBody AddonDtoRequest addonDto) {
        try {
            return ResponseEntity.ok(addonService.createAddon(addonDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/addons")
    public ResponseEntity<?> getAllAddons() {
        try {
            Optional<List<AddonDto>> addons = addonService.getAllAddonsAdmin();
            if (addons.isPresent()) {
                return ResponseEntity.ok(addons);
            } else
                return ResponseEntity.ok(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при попытке вернуть дополнения " + e.getLocalizedMessage());
        }
    }

    @PutMapping("/addons/{id}")
    public ResponseEntity<?> updateAddon(@PathVariable Long id, @RequestBody AddonDtoRequest addonDto) {
        try {
            return ResponseEntity.ok(addonService.updateAddon(id, addonDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/addons/{id}")
    public ResponseEntity<?> deleteAddon(@PathVariable Long id) {
        try {
            addonService.deleteAddon(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders() {
        try {
            return ResponseEntity.ok(orderService.getAllOrders());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

//    @PutMapping("/orders/{id}")
//    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody OrderUpdateDto dto) {
//        try {
//            if (!userService.isAuthorized(dto.getTelegramToken())) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
//            return ResponseEntity.ok(orderService.updateOrder(id, dto));
//        }catch (RuntimeException e){
//            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
//        }
//    }
}


