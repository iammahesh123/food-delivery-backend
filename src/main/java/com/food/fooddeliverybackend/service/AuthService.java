package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.UserRegisterDTO;
import com.food.fooddeliverybackend.model.UserResponseDTO;

public interface AuthService {
    String login(String username, String password);
    String register(UserRegisterDTO user);
}

