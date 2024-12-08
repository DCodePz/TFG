package com.tfg.eldest.periodo;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import com.tfg.eldest.actividadformacion.actividad.ActividadRepository;
import com.tfg.eldest.actividadformacion.formacion.FormacionRepository;
import com.tfg.eldest.usuario.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class PeriodoConfig {
    private final ActividadRepository actividadRepository;
    private final FormacionRepository formacionRepository;

    public PeriodoConfig(ActividadRepository actividadRepository, FormacionRepository formacionRepository) {
        this.actividadRepository = actividadRepository;
        this.formacionRepository = formacionRepository;
    }

    private List<ActividadFormacion> recuperarActividades() {
        return actividadRepository.findAll();
    }

    private List<ActividadFormacion> recuperarFormaciones() {
        return formacionRepository.findAll();
    }

    private List<ActividadFormacion> recuperarActividadesFormaciones() {
        List<ActividadFormacion> actividadesFormaciones = new ArrayList<>();
        actividadesFormaciones.addAll(recuperarActividades());
        actividadesFormaciones.addAll(recuperarFormaciones());

        return actividadesFormaciones;
    }

    @Bean
    @Order(2)
    CommandLineRunner commandLineRunnerPeriodo(PeriodoRepository repository) {
        return args -> {
            Periodo per1 = new Periodo(1L, "curso 22/23", LocalDate.of(2022, SEPTEMBER, 4),
                    LocalDate.of(2023, JUNE, 24));

            Periodo per2 = new Periodo(2L, "curso 23/24", LocalDate.of(2023, SEPTEMBER, 5),
                    LocalDate.of(2024, JUNE, 25));

            Periodo per3 = new Periodo(3L, "curso 24/25", LocalDate.of(2024, SEPTEMBER, 6),
                    LocalDate.of(2025, JUNE, 26));

            repository.saveAll(List.of(per1, per2, per3));
        };
    }
}
