package com.food.fooddeliverybackend.service;

import com.food.fooddeliverybackend.enums.UserRoles;
import com.food.fooddeliverybackend.model.UserRegisterDTO;
import com.food.fooddeliverybackend.model.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserResponseDTO getCurrentUser();
    UserResponseDTO updateProfile(UserRegisterDTO dto);
    void updatePassword(String oldPassword, String newPassword);
    List<UserResponseDTO> getAllUsers();
    void updateRole(Long userId, UserRoles newRole);
    void toggleUserStatus(Long userId, boolean active);

}
