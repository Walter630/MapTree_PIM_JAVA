package com.pim.MapTree.modules.funcionario.useCase;

import com.pim.MapTree.modules.funcionario.dto.FuncionarioDTO;
import com.pim.MapTree.modules.funcionario.entity.Funcionario;
import com.pim.MapTree.modules.funcionario.mapper.FuncionarioMapper;
import com.pim.MapTree.modules.funcionario.repository.FuncionarioRepository;
import com.pim.MapTree.modules.user.entity.Roles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class FuncionarioUseCaseTest {
    @Mock
    private FuncionarioRepository funcionarioRepository;
    @Mock
    private FuncionarioMapper funcionarioMapper;
    @InjectMocks
    private FuncionarioUseCase funcionarioUseCase;

    @Test
    @DisplayName("Cadastro de Funcionarios")
    void execute() throws Exception {
        var dto = new FuncionarioDTO("walter", "teste@gmail.com", "99999999999", "senha123", "88883888888", Roles.USER);

        var entidadeSimulada = new Funcionario();
        var dtoResponse = new FuncionarioDTO("walter", "teste@gmail.com", "0999999999", null, "88883888888", Roles.USER);
        // Ensinamos o Mockito como o Mapper deve se comportar
        given(funcionarioMapper.toEntity(dto)).willReturn(entidadeSimulada);
        given(funcionarioRepository.save(entidadeSimulada)).willReturn(entidadeSimulada);
        given(funcionarioMapper.toDTO(entidadeSimulada)).willReturn(dtoResponse);

        // --- 2. AÇÃO (When) ---
        var result = funcionarioUseCase.execute(dto);

        // --- 3. VERIFICAÇÃO (Then) ---
        // Verificamos se o UseCase chamou o Save do banco corretamente
        then(funcionarioRepository).should().save(entidadeSimulada);

        // Verificamos se ele retornou os dados corretos que o Mapper entregou
        assertNotNull(result);
        assertEquals("walter", result.name());
        assertNull(result.password()); // Garantindo que a senha não voltou!
    }
    @Test
    @DisplayName("Deve retorna a lista de funcionarios")
    void findAll() {
        //given teatro
        //cria a cena de mentira
        var entidade = new Funcionario();
        entidade.setName("walter");

        //criamos o DTO de mentira
        var dtoResponse = new FuncionarioDTO("walter","teste@gmail.com", "0999999999", null, "88883888888", Roles.USER);

        //O repositorio devolve a lista
        given(funcionarioRepository.findAll()).willReturn(List.of(entidade));
        given(funcionarioMapper.toDTOList(List.of(entidade))).willReturn(List.of(dtoResponse));

        //when (AÇAO
        var result = funcionarioUseCase.findAll();

        //Verificação when
        assertNotNull(result);
        assertEquals(1, result.size());

        //chama o primeiro elemento da lista
        assertEquals("walter", result.get(0).name());

        //garantimos que o usecase chamou o findAll
        then(funcionarioRepository).should().findAll();

    }

    @Test
    void updateFuncionario() {
        UUID id = UUID.randomUUID();
        var entidade = new Funcionario();
        entidade.setId(id);
        entidade.setName("walter");

        var dtoRequest = new FuncionarioDTO("Walter Atualizado", "teste2@gmail.com", "0999999999", null, "88883888888", Roles.USER);
        var dtoResponse = new FuncionarioDTO("Walter Atualizado", "teste@gmail.com", "0999999999", null, "88883888888", Roles.USER);
        given(funcionarioRepository.findById(id)).willReturn(Optional.of(entidade));

        given(funcionarioRepository.save(entidade)).willReturn(entidade);
        given(funcionarioMapper.toDTO(entidade)).willReturn(dtoResponse);

        var result = funcionarioUseCase.updateFuncionario(id, dtoRequest);

        assertNotNull(result);
        assertEquals("Walter Atualizado", result.name());

        then(funcionarioMapper).should().toDTOUpdate(dtoRequest, entidade);
        then(funcionarioRepository).should().save(entidade);
    }
}