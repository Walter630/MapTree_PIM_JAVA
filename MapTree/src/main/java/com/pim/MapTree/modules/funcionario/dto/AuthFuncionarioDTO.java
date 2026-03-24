package com.pim.MapTree.modules.funcionario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthFuncionarioDTO {
    @NotBlank(message = "Username obrigatório")
    @Schema(description = "Nome de usuário", example = "joao123")
    private String name;

    @NotBlank(message = "Senha obrigatória")
    @Size(min = 6, message = "Mínimo 6 caracteres")
    @Schema(description = "Senha do usuário", example = "senha123")
    private String password;
}
