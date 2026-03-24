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
    @Column(unique = true)
    private String cnpj;
    private String endereco;
    @Column(unique = true)
    private String telefone;
    @Column(unique = true)
    private String email;

    @CreationTimestamp
    @Column(updatable = false) // Garante que a data de criação nunca mude
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime dataAlteracao;
}