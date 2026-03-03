package com.pim.MapTree.modules.funcionario.repository;

import com.pim.MapTree.modules.funcionario.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    Optional<Funcionario> findByCpfAndEmail(String cpf, String email);

    void deleteFuncionario(Funcionario funcionario);
}
