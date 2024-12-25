package com.food.fooddeliverybackend.service.impl;

import com.food.fooddeliverybackend.entity.User;
import com.food.fooddeliverybackend.model.UserRegisterDTO;
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
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setRole(userRegisterDTO.getRole());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setEmail(userRegisterDTO.getEmail());
        userRepository.save(user);

        // Send a welcome email
        String subject = "Welcome to Food Delivery Service!";
        String message = String.format(
                "Hello %s,\n\nWelcome to Food Delivery Service! We're thrilled to have you on board.\n\nBest Regards,\nThe Team",
                user.getUsername()
        );
        emailService.sendEmail(user.getEmail(), subject, message);

        return "User registered successfully!";
    }

    @Override
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(username);
        return token;
    }
}
