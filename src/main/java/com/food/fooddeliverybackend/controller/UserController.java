package com.food.fooddeliverybackend.controller;

import com.food.fooddeliverybackend.enums.UserRoles;
import com.food.fooddeliverybackend.model.UserRegisterDTO;
import com.food.fooddeliverybackend.model.UserResponseDTO;
import com.food.fooddeliverybackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Profile and Admin User Operations")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get current logged-in user's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyProfile() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @Operation(summary = "Update current user's profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateProfile(
            @RequestBody UserRegisterDTO dto) {
        return ResponseEntity.ok(userService.updateProfile(dto));
    }

    @Operation(summary = "Change current user's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Old password incorrect"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PutMapping("/me/password")
    public ResponseEntity<String> updatePassword(
            @Parameter(description = "Old password", required = true, in = ParameterIn.QUERY)
            @RequestParam String oldPassword,

            @Parameter(description = "New password", required = true, in = ParameterIn.QUERY)
            @RequestParam String newPassword) {

        userService.updatePassword(oldPassword, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }

    // ---------------------- Admin Endpoints ------------------------

    @Operation(summary = "Get list of all users (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admins only")
    })
    @GetMapping("/admin")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Update user's role by ID (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role updated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admins only")
    })
    @PutMapping("/admin/{id}/role")
    public ResponseEntity<String> updateRole(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long id,

            @Parameter(description = "New role to assign", required = true)
            @RequestParam UserRoles role) {

        userService.updateRole(id, role);
        return ResponseEntity.ok("User role updated");
    }

    @Operation(summary = "Enable or disable a user account by ID (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User status updated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admins only")
    })
    @PutMapping("/admin/{id}/status")
    public ResponseEntity<String> toggleUserStatus(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long id,

            @Parameter(description = "Set active to true or false", required = true)
            @RequestParam boolean active) {

        userService.toggleUserStatus(id, active);
        return ResponseEntity.ok("User status updated");
    }
}
