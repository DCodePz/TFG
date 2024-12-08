package com.tfg.eldest.usuario;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class UsuarioConfig {
    @Bean
    @Order(2)
    CommandLineRunner commandLineRunnerUsuario(UsuarioRepository repository) {
        return args -> {
            Usuario usr1 = new Usuario(1L, "Socio", "password");

            Usuario usr2 = new Usuario(2L, "Monitor", "password");

            Usuario usr3 = new Usuario(3L, "Coordinador", "password");

            repository.saveAll(List.of(usr1, usr2, usr3));
        };
    }
}
