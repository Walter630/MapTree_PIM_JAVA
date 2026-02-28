package com.pim.MapTree.modules.empresa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmpresaDTO(
        @NotBlank(message = "O nome é obrigatorio")
        String nome,
        @Size(min = 14, max = 14, message = "O CNPJ deve possuir 14 caracteres") // CNPJ são 14, CPF são 11 ;)
        String cnpj,
        @NotNull(message = "O endereço não pode ser vazio")
        String endereco,
        @Size(min = 11, max = 14, message = "Telefone inválido")
        String telefone,
        @Email(message = "Email está inválido")
        String email
) {
}
