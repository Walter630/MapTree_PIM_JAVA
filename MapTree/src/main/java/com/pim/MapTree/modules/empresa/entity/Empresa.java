package com.pim.MapTree.modules.empresa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
    @CreationTimestamp
    private LocalDateTime creatAt;
    @UpdateTimestamp
    private LocalDateTime dataAlteracao;

}
