package com.pim.MapTree.modules.funcionario.controller;

import com.pim.MapTree.modules.funcionario.dto.AuthFuncionarioDTO;
import com.pim.MapTree.modules.funcionario.useCase.AuthFuncionarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Cadastro e login de usuarios")
public class AuthFuncionarioController {

    private final AuthFuncionarioUseCase authFuncionarioUseCase;

    //────────────────────────────LOGIN─────────────────────────────────────

    @Operation(summary = "Login de usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login bem sucedido, retorna token"),
            @ApiResponse(responseCode = "400", description = "Invalidade Credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<String> create(@RequestBody AuthFuncionarioDTO authFuncionarioDTO) {
        try{
            var result = this.authFuncionarioUseCase.execute(authFuncionarioDTO);
            return ResponseEntity.ok().body(result);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
