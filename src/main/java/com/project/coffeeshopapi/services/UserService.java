package com.project.coffeeshopapi.services;

import com.project.coffeeshopapi.models.User;
import com.project.coffeeshopapi.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createOrUpdateUser(Map<String, Object> userData) {
        Long telegramId = Long.valueOf(userData.get("id").toString());
        User user = userRepository.findById(telegramId).orElse(new User());

        user.setTelegramId(telegramId);
        user.setUsername((String) userData.getOrDefault("username", ""));
        user.setFirstName((String) userData.getOrDefault("first_name", ""));
        user.setLastName((String) userData.getOrDefault("last_name", ""));
        user.setPhotoUrl((String) userData.getOrDefault("photo_url", ""));

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean isAuthorized(String token) {
        return userRepository.findByTelegramId(Long.parseLong(token)).isPresent();
    }
}

