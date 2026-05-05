package com.pim.MapTree.modules.funcionario.useCase;

import com.pim.MapTree.modules.funcionario.dto.FuncionarioDTO;
import com.pim.MapTree.modules.funcionario.mapper.FuncionarioMapper;
import com.pim.MapTree.modules.funcionario.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FuncionarioUseCase {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioUseCase(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    @Transactional //usar ele nos save, delete e update que altera o banco de dados
    public FuncionarioDTO execute(FuncionarioDTO funcionario) {
        try{
            this.funcionarioRepository.findByCpfOrEmail(funcionario.cpf(), funcionario.email())
                    .ifPresent(e -> {throw new RuntimeException("Cpf ou Email ja existente");
                    });
            var entity = funcionarioMapper.toEntity(funcionario);
            var savedEntity = this.funcionarioRepository.save(entity);

            return funcionarioMapper.toDTO(savedEntity);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<FuncionarioDTO> findAll() {
        var listFuncionario = this.funcionarioRepository.findAll();
        return funcionarioMapper.toDTOList(listFuncionario);
    }

    @Transactional
    public FuncionarioDTO updateFuncionario(UUID id, FuncionarioDTO funcionario) {
        // 1. Verifica se o funcionário existe no banco de dados
        var entityExisting = this.funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nao encontrado"));

        funcionarioMapper.toDTOUpdate(funcionario, entityExisting);
        //salvo a entidade e returno transformando em dto
        var savedEntity = this.funcionarioRepository.save(entityExisting);
        return funcionarioMapper.toDTO(savedEntity);
    }

    @Transactional
    public void deleteFuncionario(UUID id) {
        this.funcionarioRepository.deleteById(id);
    }
}
