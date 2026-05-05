package com.pim.MapTree.modules.empresa.useCases;

import com.pim.MapTree.modules.empresa.dto.EmpresaDTO;
import com.pim.MapTree.modules.empresa.entity.Empresa;
import com.pim.MapTree.modules.empresa.mapper.EmpresaMapper;
import com.pim.MapTree.modules.empresa.repository.EmpresaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class EmpresaUseCaseTest {
    @Mock
    private EmpresaMapper empresaMapper;
    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpresaUseCase empresaUseCase;
    @Test
    @DisplayName("Criado empresa")
    void createEmpresa() {
        var dto = new EmpresaDTO("teste", "12121212", "rua melo", "888888888888", "teste@gmail.com");
        var empresa = new Empresa();
        var dtoResponse = new EmpresaDTO("teste", "12121212", "rua melo", "888888888888", "teste@gmail.com");

        given(empresaMapper.toEntity(dto)).willReturn(empresa);
        given(empresaRepository.save(empresa)).willReturn(empresa);
        given(empresaMapper.toDTO(empresa)).willReturn(dtoResponse);

        var result = empresaUseCase.createEmpresa(dto);

        then(empresaRepository).should().save(empresa);

        assertNotNull(result);
        assertEquals("teste", result.nome());
    }

    @Test
    void getEmpresas() {
    }

    @Test
    void getEmpresaId() {
    }
}