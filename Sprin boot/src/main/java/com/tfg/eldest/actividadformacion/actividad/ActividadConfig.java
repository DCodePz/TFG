package com.tfg.eldest.actividadformacion.actividad;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class ActividadConfig {
    @Bean
    CommandLineRunner commandLineRunnerActividad(ActividadRepository repository) {
        return args -> {
            ActividadFormacion act1 = new ActividadFormacion(1L, "Actividad", "Actividad 1",
                    LocalDate.of(2024, SEPTEMBER, 3), "Monitor 1, Monitor 2", 3,
                    2, "Pequeños", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la primera actividad",
                    "Observación 1");

            ActividadFormacion act2 = new ActividadFormacion(2L, "Actividad", "Actividad 2",
                    LocalDate.of(2024, OCTOBER, 10), "Monitor 1, Monitor 2", 3,
                    2, "Medianos", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la segunda actividad",
                    "Observación 1");

            ActividadFormacion act3 = new ActividadFormacion(3L, "Actividad", "Actividad 3",
                    LocalDate.of(2024, NOVEMBER, 24), "Monitor 1, Monitor 2", 3,
                    2, "Mayores", 30, "Objetivo 1, Objetivo 2",
                    "Material 1, Material 2, Material 3", "Es la tercera actividad",
                    "Observación 1");

            repository.saveAll(List.of(act1, act2, act3));
        };
    }
}
