package com.pim.MapTree.modules.empresa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder // Permite: Empresa.builder().nome("Tech").build()
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String cnpj;
    private String endereco;
    private String telefone;
    private String email;

    @CreationTimestamp
    @Column(updatable = false) // Garante que a data de criação nunca mude
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime dataAlteracao;
}