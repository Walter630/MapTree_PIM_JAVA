package com.pim.MapTree.modules.empresa.controller;

import com.pim.MapTree.modules.empresa.entity.Empresa;
import com.pim.MapTree.modules.empresa.useCases.EmpresaUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    @Autowired
    private EmpresaUseCase empresaUseCase;

    @GetMapping("/")
    public ResponseEntity<List<Empresa>> getEmpresas() {
        return empresaUseCase.getEmpresas();
    }

    //retorna um objeto porque pode ter sucesso ou falha
    @PostMapping("/")
    public ResponseEntity<Object> createEmpresa(@Valid @RequestBody Empresa empresa) {
        try{
            var result = this.empresaUseCase.createEmpresa(empresa);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
