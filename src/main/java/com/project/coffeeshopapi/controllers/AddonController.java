package com.project.coffeeshopapi.controllers;

import com.project.coffeeshopapi.dto.AddonDto;
import com.project.coffeeshopapi.dto.CoffeeDto;
import com.project.coffeeshopapi.services.AddonService;
import com.project.coffeeshopapi.services.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/addons")
public class AddonController {

    private final AddonService addonService;

    @Autowired
    public AddonController(AddonService addonService) {
        this.addonService = addonService;
    }


    @PostMapping()
    public ResponseEntity<?> getAllAddons(@RequestBody Map<String, String> body) {
        try {
            String token = body.get("telegram_token");
            Optional<List<AddonDto>> addons = addonService.getAllAddons(token);
            if (addons.isPresent()) {
                return ResponseEntity.ok(addons);
            } else
                return ResponseEntity.ok(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при попытке вернуть дополнения " + e.getLocalizedMessage());
        }
    }
}
