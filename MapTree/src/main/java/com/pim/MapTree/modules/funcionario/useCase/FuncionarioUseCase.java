package com.pim.MapTree.modules.funcionario.useCase;

import com.pim.MapTree.modules.funcionario.dto.FuncionarioDTO;
import com.pim.MapTree.modules.funcionario.entity.Funcionario;
import com.pim.MapTree.modules.funcionario.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FuncionarioUseCase {
    private final FuncionarioRepository funcionarioRepository;

    @Transactional
    public Funcionario execute(Funcionario funcionario) {
        try{
            this.funcionarioRepository.findByCpfAndEmail(funcionario.getCpf(), funcionario.getEmail())
                    .ifPresent(e -> {throw new RuntimeException("Cpf ou Email ja existente"); });

            return funcionarioRepository.save(funcionario);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public void deleteFuncionario(UUID id) {
        var funcionario = funcionarioRepository.findById(id).orElse(null);
        assert funcionario != null;
        funcionarioRepository.delete(funcionario);
    }

    public Funcionario updateFuncionario(Funcionario funcionario) {
        // 1. Verifica se o funcionário existe no banco de dados
        if (!funcionarioRepository.existsById(funcionario.getId())) {
            throw new EntityNotFoundException("Funcionário com ID " + funcionario.getId() + " não encontrado.");
        }
        return funcionarioRepository.save(funcionario);
    }
}
