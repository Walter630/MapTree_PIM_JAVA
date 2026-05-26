package com.pim.MapTree.modules.user.dto;

import com.pim.MapTree.modules.user.entity.Roles;

import java.util.UUID;

public record LoginResponse(
        String accessToken,
        UserResponseDTO user
) {
    public record UserResponseDTO(
            UUID id,
            String email,
            Roles role
    ){}
}
