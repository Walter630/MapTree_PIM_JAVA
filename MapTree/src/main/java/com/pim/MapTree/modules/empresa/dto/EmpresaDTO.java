package com.pim.MapTree.modules.empresa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public record EmpresaDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        @NotBlank(message = "O nome é obrigatorio")
        String nome,
        @NotBlank(message = "O CNPJ deve possuir 14 caracteres")
        @CNPJ(message = " O cnpj informado é invalido")
        String cnpj,
        @Nullable
        String endereco,
        @NotBlank(message = "O telefone é obrigatório")
        @Size(min = 10, max = 15, message = "Telefone inválido") // cobre (DD) + número com/sem 9
        String telefone,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Email está inválido")
        String email
) {
        public EmpresaDTO(String nome, String cnpj, String endereco, String telefone, String email) {
                this(null, nome, cnpj, endereco, telefone, email);
        }
}
