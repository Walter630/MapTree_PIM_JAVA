package com.pim.MapTree.modules.user.controller;

import com.pim.MapTree.modules.user.dto.UserDTO;
import com.pim.MapTree.modules.user.useCase.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Cadastro e login de usuarios")
public class UserAuthController {

    private final UserService userService;

    //────────────────────────────LOGIN─────────────────────────────────────

    @Operation(summary = "Login de usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login bem sucedido, retorna token"),
            @ApiResponse(responseCode = "400", description = "Invalidade Credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<String> create(@RequestBody UserDTO authFuncionarioDTO) {
        try{
            var result = this.userService.execute(authFuncionarioDTO);
            return ResponseEntity.ok().body(result);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    //────────────────────────────REGISTER─────────────────────────────────────
    @Operation(summary = "registro de usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "criado com sucesso!!")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO authFuncionarioDTO) {
        try{
            var result = this.userService.register(authFuncionarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
