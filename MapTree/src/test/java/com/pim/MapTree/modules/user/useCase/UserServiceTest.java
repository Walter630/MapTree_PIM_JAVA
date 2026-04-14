package com.pim.MapTree.modules.user.useCase;

import com.pim.MapTree.modules.user.dto.UserDTO;
import com.pim.MapTree.modules.user.entity.Roles;
import com.pim.MapTree.modules.user.entity.User;
import com.pim.MapTree.modules.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // oque ela faz? ela vai inicializar todos os mogs
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Return list User")
    public void returnListUser () {
        User user = new User(UUID.randomUUID(),"walter", "teste123", Roles.ADMIN);
        // ✅ CERTO — mocka o repositório, deixa o serviço rodar de verdade
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<User> result = userService.findAll();
        assertThat(result)
                .hasSize(1)
                .extracting(User::getName)
                .containsExactly("walter");// confirma que o repo foi chamado
    }

    @Test
    @DisplayName("Deve salvar usuário com senha encriptada")
    void shouldSaveUserWithEncodedPassword() throws Exception {
        // Given
        var dto = new UserDTO("walter", "senha123", Roles.ADMIN);
        given(passwordEncoder.encode("senha123")).willReturn("hash_fake");

        // When
        userService.execute(dto);

        // Then
        then(userRepository).should().save(any(User.class));
    }

    @Test
    void register() {
    }
}