package com.pim.MapTree.modules.empresa.controller;

import com.pim.MapTree.modules.empresa.dto.EmpresaDTO;
import com.pim.MapTree.modules.empresa.entity.Empresa;
import com.pim.MapTree.modules.empresa.useCases.EmpresaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/empresa")
@RequiredArgsConstructor // Substitui o @Autowired pelo construtor final
@Tag(name = "Enterprise", description = "Register, get, update enterprise")
public class EmpresaController {

    private final EmpresaUseCase empresaUseCase;

    //────────────────────────────GET_ALL_ENTERPRISE─────────────────────────────────────
    @Operation(summary = "GetAll_Enterprise")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Listado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Nenhuma empresa encontrada")
    })
    @GetMapping("/")
    public ResponseEntity<List<EmpresaDTO>> getEmpresas() {
            var empresas = empresaUseCase.getEmpresas();
            return ResponseEntity.ok(empresas);
    }

    //────────────────────────────GET_ENTERPRISE_ID─────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> getEmpresa(@PathVariable UUID id) {
        var empresa = empresaUseCase.getEmpresaId(id);
        return ResponseEntity.ok(empresa);
    }

    //────────────────────────────CREATE_ENTERPRISE─────────────────────────────────────

    @PostMapping("/")
    public ResponseEntity<Object> createEmpresa(@Valid @RequestBody EmpresaDTO data) {
        var result = this.empresaUseCase.createEmpresa(data);
        // 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //────────────────────────────UPDATE_ENTERPRISE─────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmpresa(@PathVariable UUID id, @Valid @RequestBody EmpresaDTO data) {
            var empresaEntity = Empresa.builder()
                    .nome(data.nome())
                    .email(data.email())
                    .endereco(data.endereco())
                    .telefone(data.telefone())
                    .cnpj(data.cnpj())
                    .build();
            return ResponseEntity.ok().body(empresaEntity);
    }
    //PathVariable serve para
}
