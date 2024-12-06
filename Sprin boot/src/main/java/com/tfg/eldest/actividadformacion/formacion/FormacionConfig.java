package com.tfg.eldest.actividadformacion.formacion;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import com.tfg.eldest.actividadformacion.actividad.ActividadRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class FormacionConfig {
    @Bean
    CommandLineRunner commandLineRunnerFormacion(ActividadRepository repository) {
        return args -> {
            ActividadFormacion act1 = new ActividadFormacion(4L, "Formacion", "Formacion 1",
                    LocalDate.of(2024, SEPTEMBER, 3), "Monitor 1, Monitor 2", 3,
                    2, "Pequeños", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la primera formacion",
                    "Observación 1");

            ActividadFormacion act2 = new ActividadFormacion(5L, "Formacion", "Formacion 2",
                    LocalDate.of(2024, OCTOBER, 10), "Monitor 1, Monitor 2", 3,
                    2, "Medianos", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la segunda formacion",
                    "Observación 1");

            ActividadFormacion act3 = new ActividadFormacion(6L, "Formacion", "Formacion 3",
                    LocalDate.of(2024, NOVEMBER, 24), "Monitor 1, Monitor 2", 3,
                    2, "Mayores", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la tercera formacion",
                    "Observación 1");

            repository.saveAll(List.of(act1, act2, act3));
        };
    }
}
