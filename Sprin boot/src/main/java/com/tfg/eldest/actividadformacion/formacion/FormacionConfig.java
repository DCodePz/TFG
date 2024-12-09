package com.tfg.eldest.actividadformacion.formacion;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import com.tfg.eldest.actividadformacion.actividad.ActividadRepository;
import com.tfg.eldest.periodo.Periodo;
import com.tfg.eldest.periodo.PeriodoRepository;
import com.tfg.eldest.usuario.Usuario;
import com.tfg.eldest.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class FormacionConfig {
    private final UsuarioRepository usuarioRepository;
    private final PeriodoRepository periodoRepository;

    @Autowired
    public FormacionConfig(UsuarioRepository usuarioRepository, PeriodoRepository periodoRepository) {
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
    CommandLineRunner commandLineRunnerFormacion(FormacionRepository repository) {
        return args -> {
            ActividadFormacion for1 = new ActividadFormacion(4L, "Formacion", "Formacion 1",
                    LocalDate.of(2024, SEPTEMBER, 3), recuperarUsuarios(), null, 3,
                    2, "Pequeños", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la primera formacion",
                    "Observación 1", recuperarPeriodos().get(0));

            ActividadFormacion for2 = new ActividadFormacion(5L, "Formacion", "Formacion 2",
                    LocalDate.of(2024, OCTOBER, 10), recuperarUsuarios(), null, 3,
                    2, "Medianos", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la segunda formacion",
                    "Observación 1", recuperarPeriodos().get(1));

            ActividadFormacion for3 = new ActividadFormacion(6L, "Formacion", "Formacion 3",
                    LocalDate.of(2024, NOVEMBER, 24), recuperarUsuarios(), null, 3,
                    2, "Mayores", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la tercera formacion",
                    "Observación 1", recuperarPeriodos().get(2));

            repository.saveAll(List.of(for1, for2, for3));
        };
    }
}
