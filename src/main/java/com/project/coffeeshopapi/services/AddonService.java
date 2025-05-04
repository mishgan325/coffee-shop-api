package com.project.coffeeshopapi.services;

import com.project.coffeeshopapi.dto.AddonDto;
import com.project.coffeeshopapi.dto.AddonDtoRequest;
import com.project.coffeeshopapi.dto.CoffeeDto;
import com.project.coffeeshopapi.models.Addon;
import com.project.coffeeshopapi.models.User;
import com.project.coffeeshopapi.repos.AddonRepository;
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
public class AddonService {

    private final AddonRepository addonRepository;
    private final DtoMapper dtoMapper;

    private final UserRepository userRepository;
    @Autowired
    public AddonService(AddonRepository addonRepository, DtoMapper dtoMapper, UserRepository userRepository) {
        this.addonRepository = addonRepository;
        this.dtoMapper = dtoMapper;
        this.userRepository = userRepository;
    }

    public Optional<List<AddonDto>> getAllAddons(String telegramToken) {
        Long telegramId = Long.valueOf(telegramToken);

       userRepository.findByTelegramId(telegramId).orElseThrow(() -> new RuntimeException("User not found"));
        return Optional.of(addonRepository.findAll().stream().map(dtoMapper::convertToAddonDto).collect(Collectors.toList()));
    }

    public Optional<List<AddonDto>> getAllAddonsAdmin() {
        return Optional.of(addonRepository.findAll().stream().map(dtoMapper::convertToAddonDto).collect(Collectors.toList()));
    }

    public AddonDto createAddon(AddonDtoRequest dto) {
        Addon addon = new Addon();
        addon.setName(dto.getName());
        addon.setPrice(dto.getPrice());
        return dtoMapper.convertToAddonDto(addonRepository.save(addon));
    }

    public AddonDto updateAddon(Long id, AddonDtoRequest dto) {
        Addon addon = addonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Добавка не найдена"));

        if (dto.getName() != null) addon.setName(dto.getName());
        if (dto.getPrice() != null) addon.setPrice(dto.getPrice());

        return dtoMapper.convertToAddonDto(addonRepository.save(addon));
    }

    public void deleteAddon(Long id) {
        if (!addonRepository.existsById(id)) {
            throw new NotFoundException("Добавка не найдена");
        }
        addonRepository.deleteById(id);
    }
}
