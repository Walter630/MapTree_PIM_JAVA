package com.pim.MapTree.modules.funcionario.dto;

import com.pim.MapTree.modules.user.entity.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

public record FuncionarioDTO(
        @NotBlank(message = "Nome é obrigatorio")
        String name,

        @NotNull
        @Email(message = "O campo email esta invalido")
        String email,

        @Size(min = 11, max = 14)
        String cpf,

        @Size(min = 1, max = 50)
        @NotNull
        String password,
        String phone,
        Roles role
        ) {
}
