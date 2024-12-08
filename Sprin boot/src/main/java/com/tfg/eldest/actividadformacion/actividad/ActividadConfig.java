package com.tfg.eldest.actividadformacion.actividad;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import com.tfg.eldest.periodo.Periodo;
import com.tfg.eldest.periodo.PeriodoRepository;
import com.tfg.eldest.usuario.Usuario;
import com.tfg.eldest.usuario.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class ActividadConfig {
    private final UsuarioRepository usuarioRepository;
    private final PeriodoRepository periodoRepository;

    public ActividadConfig(UsuarioRepository usuarioRepository, PeriodoRepository periodoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.periodoRepository = periodoRepository;
    }

    private List<Usuario> recuperarUsuarios() {
        return usuarioRepository.findAll();
    }

    private List<Periodo> recuperarPeriodos() {
        return periodoRepository.findAll();
    }

    @Bean
    @Order(3)
    CommandLineRunner commandLineRunnerActividad(ActividadRepository repository) {
        return args -> {
            ActividadFormacion act1 = new ActividadFormacion(1L, "Actividad", "Actividad 1",
                    LocalDate.of(2024, SEPTEMBER, 3), recuperarUsuarios(), recuperarUsuarios(), 3,
                    2, "Pequeños", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la primera actividad",
                    "Observación 1", recuperarPeriodos().get(0));

            ActividadFormacion act2 = new ActividadFormacion(2L, "Actividad", "Actividad 2",
                    LocalDate.of(2024, OCTOBER, 10), recuperarUsuarios(), recuperarUsuarios(), 3,
                    2, "Medianos", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la segunda actividad",
                    "Observación 1", recuperarPeriodos().get(1));

            ActividadFormacion act3 = new ActividadFormacion(3L, "Actividad", "Actividad 3",
                    LocalDate.of(2024, NOVEMBER, 24), recuperarUsuarios(), recuperarUsuarios(), 3,
                    2, "Mayores", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la tercera actividad",
                    "Observación 1", recuperarPeriodos().get(2));

            repository.saveAll(List.of(act1, act2, act3));
        };
    }
}
