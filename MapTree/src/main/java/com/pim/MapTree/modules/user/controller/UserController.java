package com.pim.MapTree.modules.user.controller;

import com.pim.MapTree.modules.user.dto.LoginResponse;
import com.pim.MapTree.modules.user.useCase.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me/profile")
    public ResponseEntity<LoginResponse.UserResponseDTO> getCurrentUser(Authentication authentication) {
        var user = userService.findCurrentUser(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        var users = userService.findAll().stream()
                .map(user -> Map.of(
                        "id", user.getId(),
                        "name", user.getEmail(),
                        "cpf", "",
                        "email", user.getEmail(),
                        "phone", "",
                        "role", user.getRole(),
                        "isActive", true
                ))
                .toList();

        return ResponseEntity.ok(users);
    }
}
