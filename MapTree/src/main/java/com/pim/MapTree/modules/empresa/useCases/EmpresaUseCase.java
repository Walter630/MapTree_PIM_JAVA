package com.pim.MapTree.modules.empresa.useCases;

import com.pim.MapTree.modules.empresa.entity.Empresa;
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

    @Transactional // Garante que se algo falhar, nada seja salvo no banco
    public Empresa createEmpresa(Empresa empresa) {
        // Validação 1
        empresaRepository.findByNomeOrEmail(empresa.getNome(), empresa.getEmail())
                .ifPresent(e -> { throw new RuntimeException("Nome ou Email já em uso"); });

        // Validação 2
        empresaRepository.findByCnpjOrTelefone(empresa.getCnpj(), empresa.getTelefone())
                .ifPresent(e -> { throw new RuntimeException("CNPJ ou Telefone já em uso"); });

        return empresaRepository.save(empresa);
    }

    public List<Empresa> getEmpresas() {
        return empresaRepository.findAll();
    }

    public Empresa getEmpresaId(UUID id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa nao encontrada"));
    }

    public Empresa deleteEmpresa(UUID id) {
         var empresa = empresaRepository.findById(id);
         return empresaRepository.delete(empresa);
    }
}