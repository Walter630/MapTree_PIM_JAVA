package com.pim.MapTree.modules.empresa.useCases;

import com.pim.MapTree.modules.empresa.entity.Empresa;
import com.pim.MapTree.modules.empresa.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaUseCase {
    @Autowired
    private EmpresaRepository empresaRepository;

    public ResponseEntity<String> createEmpresa(Empresa empresa) {
        this.empresaRepository
                .findByNomeOrEmail(empresa.getNome(), empresa.getEmail())
                .ifPresent((user) -> {
                    throw new RuntimeException("Empresa ja cadastrada");
                });
        this.empresaRepository
                .findByCnpjOrTelefone(empresa.getCnpj(), empresa.getTelefone())
                .ifPresent((user) -> {
                    throw new RuntimeException("Empresa ja cadastrada");
                });
        empresaRepository.save(empresa);
        return ResponseEntity.ok().body("Empresa criada com sucesso");
    }

    public ResponseEntity<List<Empresa>> getEmpresas() {
        empresaRepository.findAll();
        return ResponseEntity.ok().body(empresaRepository.findAll());
    }
}
