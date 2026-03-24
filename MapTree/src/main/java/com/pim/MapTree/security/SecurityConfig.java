package com.pim.MapTree.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//ele vai ser sempre carregado qunado eu executar o sistema
@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    //ele vai sobrescrever o secutiry padrao do sping e estamos criando um
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    // ✅ Rotas públicas — não precisam de token
                    auth.requestMatchers(
                                    "/auth/login",
                                    "/auth/register",
                                    "/auth/refreshToken",
                                    "/funcionario/",
                                    "/empresa/",
                                    // ✅ Swagger — libera tudo relacionado
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/api-docs/**",
                                    "/v3/api-docs/**"
                            ).permitAll();
                    // 🔒 Tudo mais exige autenticação
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
