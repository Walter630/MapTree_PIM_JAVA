package com.pim.MapTree.modules.funcionario.useCase;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pim.MapTree.modules.funcionario.dto.AuthFuncionarioDTO;
import com.pim.MapTree.modules.funcionario.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthFuncionarioUseCase {

    @Value("${security.token}")
    private String secretKey;

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public String execute(AuthFuncionarioDTO authFuncionarioDTO) throws AuthenticationException {
        //verifica se existe um nome do funcionario
        var funcionario = this.funcionarioRepository.findByName(authFuncionarioDTO.getName()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("User not Found");
                });
        // verificar a senha se sao iguais
        var passwordMatches = this.passwordEncoder.matches(authFuncionarioDTO.getPassword(), funcionario.getPassword());
        // se nao for igual -> erro
        if(!passwordMatches) {
            throw new AuthenticationException("");
        }
        // se for igual -> gera o toke
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("maptree")
                //tempo de expiraaco do token
                .withExpiresAt(Instant.now().plus(Duration.ofMinutes(1)))
                .withSubject(funcionario.getId().toString())
                .sign(algorithm);
        return token;
    }
}
