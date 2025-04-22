package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.model.UserLoginDTO;
import com.food.fooddeliverybackend.model.UserRegisterDTO;
import com.food.fooddeliverybackend.model.UserResponseDTO;
import com.food.fooddeliverybackend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth Controller", description = "Endpoints for User Registration and Login")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user by accepting name, email, password, role, and other optional fields."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or user already exists", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody(
                    description = "User registration details",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserRegisterDTO.class),
                            examples = @ExampleObject(
                                    value = """
                            {
                              "name": "John Doe",
                              "email": "john@example.com",
                              "password": "strongpassword123",
                              "address": "123 Main St, City",
                              "role": "CUSTOMER"
                            }
                            """
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(authService.register(userRegisterDTO));
    }

    @Operation(
            summary = "Login user",
            description = "Logs in a user by validating credentials and returns profile information with role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Invalid email or password", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(
            @RequestBody(
                    description = "User login credentials",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UserLoginDTO.class),
                            examples = @ExampleObject(
                                    value = """
                            {
                              "email": "john@example.com",
                              "password": "strongpassword123"
                            }
                            """
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(authService.login(userLoginDTO));
    }
}
