package com.pim.MapTree.modules.empresa.repository;

import com.pim.MapTree.modules.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {
    Optional<Empresa> findById(UUID id);
    // Busca empresa por nome ou email automaticamente por causa do spring data jpa
    // Optional serve para evitar null pointer exception e nos da opcao de verificar se existe ou nao
    Optional<Empresa> findByNomeOrEmail(String nome, String email);
    Optional<Empresa> findByCnpjOrTelefone(String cnpj, String telefone);
}
