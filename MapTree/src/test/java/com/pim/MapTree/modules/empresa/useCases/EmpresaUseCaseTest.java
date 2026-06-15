package com.pim.MapTree.modules.empresa.useCases;

import com.pim.MapTree.modules.empresa.dto.EmpresaDTO;
import com.pim.MapTree.modules.empresa.entity.Empresa;
import com.pim.MapTree.modules.empresa.mapper.EmpresaMapper;
import com.pim.MapTree.modules.empresa.repository.EmpresaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EmpresaUseCaseTest {

    private final EmpresaMapper empresaMapper = Mappers.getMapper(EmpresaMapper.class);
    // 2. Mantemos o @Mock APENAS para o banco de dados.
    @Mock
    private EmpresaRepository empresaRepository;

    private EmpresaUseCase empresaUseCase;

    @BeforeEach
    void setUp() {
        // Instacia manualmente o EnterpriseCase
        this.empresaUseCase = new EmpresaUseCase(empresaRepository, empresaMapper);
    }

    @Test
    @DisplayName("Criado empresa")
    void createEmpresa() {
        //Dado que
        var dto = new EmpresaDTO("teste", "12345678000199", "rua melo", "888888888888", "teste@gmail.com");
        var empresa = empresaMapper.toEntity(dto);

        given(empresaRepository.save(any(Empresa.class))).willReturn(empresa);

        var result = empresaUseCase.createEmpresa(dto);

        ArgumentCaptor<Empresa> captor = ArgumentCaptor.forClass(Empresa.class);
        //Quando
        then(empresaRepository).should().save(captor.capture());
        Empresa actual = captor.getValue();

        //Entao execute isso
        assertEquals("teste", actual.getNome());
        assertEquals("12345678000199", actual.getCnpj());

        assertNotNull(result);
        assertEquals("teste", result.nome());
    }

    @Test
    @DisplayName("Deve retornar uma lista de empresas")
    void getEmpresas() {
        // Arrange (Dado)
        var dto1 = new EmpresaDTO("teste1", "1111", "rua A", "111", "1@gmail.com");
        var dto2 = new EmpresaDTO("teste2", "2222", "rua B", "222", "2@gmail.com");

        var empresa1 = empresaMapper.toEntity(dto1);
        var empresa2 = empresaMapper.toEntity(dto2);
        var empresas = List.of(empresa1, empresa2);

        given(empresaRepository.findAll()).willReturn(empresas);

        // Act (Quando)
        var result = empresaUseCase.getEmpresas();

        // Assert (Então)
        then(empresaRepository).should().findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("teste1", result.get(0).nome());
        assertEquals("teste2", result.get(1).nome());
    }

    @Test
    @DisplayName("Deve retornar uma empresa buscando pelo ID")
    void getEmpresaId() {
        // Arrange (Dado)
        var id = UUID.randomUUID();
        var dto = new EmpresaDTO(id, "teste", "1111", "rua A", "111", "1@gmail.com");
        var empresa = empresaMapper.toEntity(dto);
        // O repositório geralmente retorna um Optional no findById
        given(empresaRepository.findById(id)).willReturn(java.util.Optional.of(empresa));

        // Act (Quando)
        var result = empresaUseCase.getEmpresaId(id);

        // Assert (Então)
        then(empresaRepository).should().findById(id);
        assertNotNull(result);
        assertEquals("teste",  result.nome());
    }

    @Test
    @DisplayName("Deve lançar uma exception")
    void getEmpresaIdException() {
        var id = UUID.randomUUID();

        given(empresaRepository.findById(id)).willReturn(java.util.Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            empresaUseCase.getEmpresaId(id);
        });

        then(empresaRepository).should().findById(id);
    }
}