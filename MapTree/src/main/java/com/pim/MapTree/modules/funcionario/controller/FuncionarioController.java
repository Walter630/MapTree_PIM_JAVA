package com.pim.MapTree.modules.funcionario.controller;

import com.pim.MapTree.modules.funcionario.dto.FuncionarioDTO;
import com.pim.MapTree.modules.funcionario.entity.Funcionario;
import com.pim.MapTree.modules.funcionario.useCase.FuncionarioUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funcionario")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioUseCase funcionarioUseCase;

    @PostMapping()
    public ResponseEntity<Object> saveFuncionario(@Valid @RequestBody FuncionarioDTO dto) {
        var func = this.funcionarioUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(func);
    }

    @GetMapping()
    public ResponseEntity<List<FuncionarioDTO>> getFuncionario() {
        var list = this.funcionarioUseCase.findAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFuncionario(@Valid @RequestBody FuncionarioDTO dto, @PathVariable UUID id) {
        var updated = this.funcionarioUseCase.updateFuncionario(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFuncionario(@PathVariable UUID id) {
        this.funcionarioUseCase.deleteFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}
