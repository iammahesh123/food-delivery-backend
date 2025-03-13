package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.UserEntity;
import com.food.fooddeliverybackend.model.UserLoginDTO;
import com.food.fooddeliverybackend.model.UserRegisterDTO;
import com.food.fooddeliverybackend.model.UserResponseDTO;
import com.food.fooddeliverybackend.repository.UserRepository;
import com.food.fooddeliverybackend.security.JwtUtil;
import com.food.fooddeliverybackend.service.AuthService;
import com.food.fooddeliverybackend.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @Override
    public String register(UserRegisterDTO userRegisterDTO) {
        // Check if username already exists
        if (userRepository.findByUsername(userRegisterDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegisterDTO.getUsername());
        userEntity.setRole(userRegisterDTO.getRole());
        userEntity.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userEntity.setEmail(userRegisterDTO.getEmail());
        userRepository.save(userEntity);

        // Send a welcome email
        String subject = "Welcome to Food Delivery Service!";
        String message = String.format(
                "Hello %s,\n\nWelcome to Food Delivery Service! We're thrilled to have you on board.\n\nBest Regards,\nThe Team",
                userEntity.getUsername()
        );
        emailService.sendEmail(userEntity.getEmail(), subject, message);

        return "User registered successfully!";
    }

    @Override
    public UserResponseDTO login(UserLoginDTO userLoginDTO) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check password
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(username);
        responseDTO.setToken(token);
        responseDTO.setRole(String.valueOf(userEntity.getRole()));
        responseDTO.setCreatedAt(userEntity.getCreatedAt());
        responseDTO.setUpdatedAt(userEntity.getUpdatedAt());
        return responseDTO;
    }
}
