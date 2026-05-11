package com.pim.MapTree.modules.funcionario.dto;

import com.pim.MapTree.modules.user.entity.Roles;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

public record FuncionarioDTO(
        @NotBlank(message = "Nome é obrigatorio")
        String name,

        @NotNull
        @Email(message = "O campo email esta invalido")
        String email,

        @NotBlank @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 digitos numericos")
        String cpf,

        @NotBlank @Size(min = 11, max = 14)
        String password,
        String phone,
        Roles role
        ) {
}
