package com.pim.MapTree.modules.user.useCase;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pim.MapTree.infra.exception.user.InvalidCredentialException;
import com.pim.MapTree.infra.exception.user.UserExisting;
import com.pim.MapTree.infra.exception.user.UserNotFound;
import com.pim.MapTree.modules.user.dto.UserDTO;
import com.pim.MapTree.modules.user.entity.Roles;
import com.pim.MapTree.modules.user.mapper.UserMapper;
import com.pim.MapTree.modules.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import com.pim.MapTree.modules.user.entity.User;
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
    private final UserMapper userMapper;

    @Transactional
    public String execute(UserDTO userDTO) {
        //verifica se existe um nome do funcionário
        var user = this.userRepository.findByName(userDTO.getName()).orElseThrow(
                () -> new UserNotFound("User not Found"));
        // verificar a senha se são iguais
        var passwordMatches = this.passwordEncoder.matches(userDTO.getPassword(), user.getPassword());
        // se não for igual -> erro
        if(!passwordMatches) {
            throw new InvalidCredentialException("Invalid Credential");
        }
        // se for igual -> gera o toke
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer("maptree_db")
                .withExpiresAt(Instant.now().plus(Duration.ofMinutes(10)))//tempo de expiraaco do token
                .withSubject(user.getId().toString())
                .sign(algorithm);
    }

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        userRepository.findByName(userDTO.getName()).ifPresent(f -> {
            throw new UserExisting("User is existing");
        });

        if(userDTO.getRole() == null) {
            userDTO.setRole(Roles.USER);
        }

        var user = userMapper.toEntity(userDTO);
        var useRepo = this.userRepository.save(user);

        return userMapper.toDTO(useRepo);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
 }
