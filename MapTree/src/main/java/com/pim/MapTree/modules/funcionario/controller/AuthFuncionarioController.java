package com.pim.MapTree.modules.funcionario.controller;

import com.pim.MapTree.modules.funcionario.dto.AuthFuncionarioDTO;
import com.pim.MapTree.modules.funcionario.useCase.AuthFuncionarioUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthFuncionarioController {

    private final AuthFuncionarioUseCase authFuncionarioUseCase;

    @PostMapping("/login")
    public ResponseEntity<Object> create(@RequestBody AuthFuncionarioDTO authFuncionarioDTO) {
        try{
            var result = this.authFuncionarioUseCase.execute(authFuncionarioDTO);
            return ResponseEntity.ok().body(result);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}
