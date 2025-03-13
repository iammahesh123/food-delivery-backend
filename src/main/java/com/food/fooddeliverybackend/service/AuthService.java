package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.model.UserLoginDTO;
import com.food.fooddeliverybackend.model.UserRegisterDTO;
import com.food.fooddeliverybackend.model.UserResponseDTO;

public interface AuthService {
    UserResponseDTO login(UserLoginDTO userLoginDTO);
    String register(UserRegisterDTO user);
}

