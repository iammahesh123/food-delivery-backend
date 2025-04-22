package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.UserEntity;
import com.food.fooddeliverybackend.enums.UserRoles;
import com.food.fooddeliverybackend.model.UserRegisterDTO;
import com.food.fooddeliverybackend.model.UserResponseDTO;
import com.food.fooddeliverybackend.repository.UserRepository;
import com.food.fooddeliverybackend.security.JwtUtil;
import com.food.fooddeliverybackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private UserEntity getAuthenticatedUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserResponseDTO getCurrentUser() {
        UserEntity user = getAuthenticatedUserEntity();
        return mapToResponse(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateProfile(UserRegisterDTO dto) {
        UserEntity user = getAuthenticatedUserEntity();

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());

        // Update only if role is provided
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        UserEntity user = getAuthenticatedUserEntity();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateRole(Long userId, UserRoles newRole) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(newRole);
        userRepository.save(user);
    }

    @Override
    public void toggleUserStatus(Long userId, boolean active) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(active);
        userRepository.save(user);
    }

    private UserResponseDTO mapToResponse(UserEntity user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setToken(jwtUtil.generateToken(user.getUsername()));
        dto.setRole(String.valueOf(user.getRole()));
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}

