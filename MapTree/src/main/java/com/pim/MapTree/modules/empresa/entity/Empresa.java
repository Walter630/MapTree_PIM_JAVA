package com.pim.MapTree.modules.empresa.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Valid
public class Empresa {
    @NotNull
    private UUID id;

    @NotBlank(message = "O nome é obrigatorio")
    private String nome;

    @Size(min = 11, message = "O cnpj deve possuir 11 caracteres")
    private String cnpj;

    @NotNull(message = "O endereço nao pode ser vazio")
    private String endereco;

    @Size(min = 11, max = 14, message = "O telefone deve conter 11 caracteres")
    private String telefone;

    @Email(message = "Email esta invalido")
    private String email;
    private Date dataCadastro;
    private Date dataAlteracao;
}
