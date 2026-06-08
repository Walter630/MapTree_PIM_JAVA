package com.pim.MapTree.modules.funcionario.useCase;

import com.pim.MapTree.infra.exception.funcionarios.CPFOrEmailIsExisting;
import com.pim.MapTree.infra.exception.funcionarios.FuncionarioNotFound;
import com.pim.MapTree.modules.funcionario.dto.FuncionarioDTO;
import com.pim.MapTree.modules.funcionario.mapper.FuncionarioMapper;
import com.pim.MapTree.modules.funcionario.repository.FuncionarioRepository;
import com.pim.MapTree.modules.user.entity.Roles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FuncionarioUseCase {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;

    @Transactional //usar ele nos save, delete e update que altera o banco de dados
    public FuncionarioDTO execute(FuncionarioDTO funcionario) {
            this.funcionarioRepository.findByCpfOrEmail(funcionario.cpf(), funcionario.email())
                    .ifPresent(e -> {throw new CPFOrEmailIsExisting("Cpf ou Email ja existente");
                    });

            if (funcionario.role() == Roles.ADMIN) {
                log.warn("Funcionario com o administrador {}", funcionario.role());
                throw new IllegalArgumentException("Perfil ADMIN nao pode ser usado para funcionario");
            }

            var dto = funcionario.role() == null
                    ? new FuncionarioDTO(funcionario.name(), funcionario.email(), funcionario.cpf(),
                    funcionario.password(), funcionario.phone(), Roles.USER)
                    : funcionario;

            var entity = funcionarioMapper.toEntity(dto);
            var savedEntity = this.funcionarioRepository.save(entity);
            log.info("Funcionario salvo com sucesso: {}", savedEntity);

            return funcionarioMapper.toDTO(savedEntity);
    }

    @Transactional(readOnly = true)
    public List<FuncionarioDTO> findAll() {
        var listFuncionario = this.funcionarioRepository.findAll();
        return funcionarioMapper.toDTOList(listFuncionario);
    }

    @Transactional
    public FuncionarioDTO updateFuncionario(UUID id, FuncionarioDTO funcionario) {
        // 1. Verifica se o funcionário existe no banco de dados
        var entityExisting = this.funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNotFound("Nao encontrado"));

        if (funcionario.role() == Roles.ADMIN) {
            throw new IllegalArgumentException("Perfil ADMIN nao pode ser usado para funcionario");
        }

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
