package com.pim.MapTree.modules.empresa.controller;

import com.pim.MapTree.modules.empresa.entity.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    @Autowired
    private EmpresaController empresaController;

    @GetMapping("/")
    public ResponseEntity<String> getEmpresas() {
        return ResponseEntity.ok().body("Empresas");
    }

    @PostMapping("/")
    public ResponseEntity<String> createEmpresa(Empresa empresa) {
        return ResponseEntity.ok().body(empresaController.createEmpresa(empresa).getBody());
    }
}
