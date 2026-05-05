package com.pim.MapTree.modules.empresa.useCases;

import com.pim.MapTree.infra.exception.enterprise.CNPJExistingEnterpriseError;
import com.pim.MapTree.infra.exception.enterprise.EnterpriseExisting;
import com.pim.MapTree.infra.exception.enterprise.EnterpriseNotFound;
import com.pim.MapTree.modules.empresa.dto.EmpresaDTO;
import com.pim.MapTree.modules.empresa.mapper.EmpresaMapper;
import com.pim.MapTree.modules.empresa.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor; // Gera o construtor automaticamente
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Injeção de dependência moderna via construtor
public class EmpresaUseCase {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

    @Transactional // Garante que se algo falhar, nada seja salvo no banco
    public EmpresaDTO createEmpresa(EmpresaDTO empresa) {
        // Validação 1
        empresaRepository.findByNomeOrEmail(empresa.nome(), empresa.email())
                .ifPresent(e -> { throw new EnterpriseExisting("Nome ou Email já em uso"); });

        // Validação 2
        empresaRepository.findByCnpjOrTelefone(empresa.cnpj(), empresa.telefone())
                .ifPresent(e -> { throw new CNPJExistingEnterpriseError("CNPJ ou Telefone já em uso"); });

        var savedEmpresa = empresaRepository.save(empresaMapper.toEntity(empresa));
        return empresaMapper.toDTO(savedEmpresa);
    }

    @Transactional(readOnly = true)
    public List<EmpresaDTO> getEmpresas() {
        return empresaMapper.toDTOList(empresaRepository.findAll());
    }

    @Transactional
    public EmpresaDTO getEmpresaId(UUID id) {
        var empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EnterpriseNotFound("Empresa nao encontrada"));
        return empresaMapper.toDTO(empresa);
    }
}