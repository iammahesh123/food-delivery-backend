package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.model.UserRegisterDTO;
import com.food.fooddeliverybackend.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth Controller", description = "List of Authentication Endpoints")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(authService.register(userRegisterDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(authService.login(username, password));
    }
}
