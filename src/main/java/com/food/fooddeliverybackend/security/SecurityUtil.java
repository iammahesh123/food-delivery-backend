package com.food.fooddeliverybackend.security;

import com.food.fooddeliverybackend.entity.UserEntity;
import com.food.fooddeliverybackend.exception.ResourceNotFoundException;
import com.food.fooddeliverybackend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Convenience accessor for the currently authenticated user.
 */
@Component
public class SecurityUtil {

    private final UserRepository userRepository;

    public SecurityUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static AuthUser getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AuthUser authUser) {
            return authUser;
        }
        return null;
    }

    public static Long getCurrentUserId() {
        AuthUser principal = getPrincipal();
        return principal != null ? principal.getUserId() : null;
    }

    public static String getCurrentUsername() {
        AuthUser principal = getPrincipal();
        return principal != null ? principal.getUsername() : null;
    }

    public UserEntity getCurrentUser() {
        Long id = getCurrentUserId();
        if (id == null) {
            throw new ResourceNotFoundException("No authenticated user in context");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found: " + id));
    }
}
