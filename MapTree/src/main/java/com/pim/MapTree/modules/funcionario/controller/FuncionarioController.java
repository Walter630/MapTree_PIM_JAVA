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

    @PostMapping("/")
    public ResponseEntity<Object> saveFuncionario(@Valid @RequestBody FuncionarioDTO dto) {
        try{
            var result = Funcionario.builder()
                    .email(dto.email())
                    .name(dto.name())
                    .cpf(dto.cpf())
                    .password(dto.password())
                    .phone(dto.phone())
                    .build();
            var func = this.funcionarioUseCase.execute(result);
            return ResponseEntity.status(HttpStatus.CREATED).body(func);
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/")
    public List<Funcionario> getFuncionario() {
        return this.funcionarioUseCase.findAll();
    }



    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFuncionario(@Valid @RequestBody FuncionarioDTO dto, @PathVariable UUID id) {
        try{
            var result = Funcionario.builder()
                    .id(id)
                    .email(dto.email())
                    .name(dto.name())
                    .cpf(dto.cpf())
                    .password(dto.password())
                    .phone(dto.phone())
                    .build();
            var updated = this.funcionarioUseCase.updateFuncionario(result);
            return ResponseEntity.ok(updated);
        }  catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
