package com.project.coffeeshopapi.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coffeeshopapi.dto.InitDataRequest;
import com.project.coffeeshopapi.dto.UserOrderResponse;
import com.project.coffeeshopapi.models.User;
import com.project.coffeeshopapi.services.OrderService;
import com.project.coffeeshopapi.services.UserService;
import com.project.coffeeshopapi.utils.TelegramAuthValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final TelegramAuthValidator authValidator;
    private final UserService userService;
    private final OrderService orderService;

    public UserController(TelegramAuthValidator authValidator, UserService userService, OrderService orderService) {
        this.authValidator = authValidator;
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody InitDataRequest initData) {
        if (!authValidator.isValid(initData.getInitData())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Telegram signature");
        }
        var allParams = parseQuery(initData.getInitData());
        String userJson = allParams.get("user");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> userData;
        try {
            userData = mapper.readValue(userJson, new TypeReference<>() {});
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid user JSON");
        }
        User user = userService.createOrUpdateUser(userData);

        return ResponseEntity.ok(user);
    }





    @PostMapping("/me/orders")
    public ResponseEntity<?> getMyOrders(@RequestBody Map<String, String> body) {
        String token = body.get("telegram_token");
        if (token == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing telegram_token"));
        }
        try {
            List<UserOrderResponse> orders = orderService.getUserOrders(token);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    private Map<String, String> parseQuery(String rawQuery) {
        return Arrays.stream(rawQuery.split("&"))
                .map(param -> param.split("=", 2))
                .collect(Collectors.toMap(
                        kv -> URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                        kv -> URLDecoder.decode(kv[1], StandardCharsets.UTF_8)
                ));
    }
}

