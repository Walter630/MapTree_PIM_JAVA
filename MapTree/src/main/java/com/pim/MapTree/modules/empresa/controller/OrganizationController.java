package com.pim.MapTree.modules.empresa.controller;

import com.pim.MapTree.modules.empresa.useCases.EmpresaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final EmpresaUseCase empresaUseCase;

    @GetMapping
    public ResponseEntity<?> getOrganizations() {
        var organizations = empresaUseCase.getEmpresas().stream()
                .map(empresa -> Map.of(
                        "id", empresa.id(),
                        "name", empresa.nome(),
                        "taxId", empresa.cnpj(),
                        "isOutsourced", false,
                        "manager", Map.of("name", "Sem gestor"),
                        "isActive", true
                ))
                .toList();

        return ResponseEntity.ok(organizations);
    }
}
