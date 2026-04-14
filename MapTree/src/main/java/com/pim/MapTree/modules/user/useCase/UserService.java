package com.pim.MapTree.modules.user.useCase;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pim.MapTree.modules.funcionario.repository.FuncionarioRepository;
import com.pim.MapTree.modules.user.dto.UserDTO;
import com.pim.MapTree.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import com.pim.MapTree.modules.user.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${security.token}")
    private String secretKey;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public String execute(UserDTO userDTO) throws AuthenticationException {
        //verifica se existe um nome do funcionario
        var user = this.userRepository.findByName(userDTO.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User not Found"));
        // verificar a senha se sao iguais
        var passwordMatches = this.passwordEncoder.matches(userDTO.getPassword(), user.getPassword());
        // se nao for igual -> erro
        if(!passwordMatches) {
            throw new AuthenticationException("");
        }
        // se for igual -> gera o toke
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("maptree_db")
                //tempo de expiraaco do token
                .withExpiresAt(Instant.now().plus(Duration.ofMinutes(10)))
                .withSubject(user.getId().toString())
                .sign(algorithm);
        return token;
    }

    public User register(UserDTO userDTO) {
        userRepository.findByName(userDTO.getName()).ifPresent(f -> {
            throw new RuntimeException("Funcionario ja cadastrado");
        });
        var userCreate = User.builder()
                .name(userDTO.getName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();

        return userRepository.save(userCreate);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
 }
