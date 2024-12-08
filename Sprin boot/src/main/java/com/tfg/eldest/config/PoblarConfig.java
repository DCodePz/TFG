package com.tfg.eldest.config;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import com.tfg.eldest.actividadformacion.actividad.ActividadRepository;
import com.tfg.eldest.actividadformacion.formacion.FormacionRepository;
import com.tfg.eldest.periodo.Periodo;
import com.tfg.eldest.periodo.PeriodoRepository;
import com.tfg.eldest.usuario.Usuario;
import com.tfg.eldest.usuario.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class PoblarConfig {
//    private UsuarioRepository usuarioRepository;
//    private ActividadRepository actividadRepository;
//    private FormacionRepository formacionRepository;
//
//    private Usuario usr1 = new Usuario();
//    private Usuario usr2 = new Usuario();
//    private Usuario usr3 = new Usuario();
//    private Periodo per1 = new Periodo();
//    private Periodo per2 = new Periodo();
//    private Periodo per3 = new Periodo();
//    private ActividadFormacion act1 = new ActividadFormacion();
//    private ActividadFormacion act2 = new ActividadFormacion();
//    private ActividadFormacion act3 = new ActividadFormacion();
//    private ActividadFormacion for1 = new ActividadFormacion();
//    private ActividadFormacion for2 = new ActividadFormacion();
//    private ActividadFormacion for3 = new ActividadFormacion();
//
//    @Autowired
//    public PoblarConfig(UsuarioRepository usuarioRepository, ActividadRepository actividadRepository, FormacionRepository formacionRepository) {
//        this.usuarioRepository = usuarioRepository;
//        this.actividadRepository = actividadRepository;
//        this.formacionRepository = formacionRepository;
//    }
//
//    @PostConstruct
//    public void crearDatos() {
//        usr1 = new Usuario(1L, "Socio", "password");
//        usr2 = new Usuario(2L, "Monitor", "password");
//        usr3 = new Usuario(3L, "Coordinador", "password");
//
//        per1 = new Periodo(1L, "curso 22/23", LocalDate.of(2022, SEPTEMBER, 4),
//                LocalDate.of(2023, JUNE, 24));
//        per2 = new Periodo(2L, "curso 23/24", LocalDate.of(2023, SEPTEMBER, 5),
//                LocalDate.of(2024, JUNE, 25));
//        per3 = new Periodo(3L, "curso 24/25", LocalDate.of(2024, SEPTEMBER, 6),
//                LocalDate.of(2025, JUNE, 26));
//
//        act1 = new ActividadFormacion(1L, "Actividad", "Actividad 1",
//                LocalDate.of(2024, SEPTEMBER, 3), List.of(usr1, usr2, usr3), 3,
//                2, "Pequeños", 30, "Objetivo 1, Objetivo 2",
//                "Material 1, Material 2, Material 3", "Es la primera actividad",
//                "Observación 1", per1);
//        act2 = new ActividadFormacion(2L, "Actividad", "Actividad 2",
//                LocalDate.of(2024, OCTOBER, 10), List.of(usr1, usr2, usr3), 3,
//                2, "Medianos", 30, "Objetivo 1, Objetivo 2",
//                "Material 1, Material 2, Material 3", "Es la segunda actividad",
//                "Observación 1", per2);
//        act3 = new ActividadFormacion(3L, "Actividad", "Actividad 3",
//                LocalDate.of(2024, NOVEMBER, 24), List.of(usr1, usr2, usr3), 3,
//                2, "Mayores", 30, "Objetivo 1, Objetivo 2",
//                "Material 1, Material 2, Material 3", "Es la tercera actividad",
//                "Observación 1", per3);
//
//        for1 = new ActividadFormacion(4L, "Formacion", "Formacion 1",
//                LocalDate.of(2024, SEPTEMBER, 3), List.of(usr1, usr2, usr3), 3,
//                2, "Pequeños", 30, "Objetivo 1, Objetivo 2",
//                "Material 1, Material 2, Material 3", "Es la primera formacion",
//                "Observación 1", per1);
//
//        for2 = new ActividadFormacion(5L, "Formacion", "Formacion 2",
//                LocalDate.of(2024, OCTOBER, 10), List.of(usr1, usr2, usr3), 3,
//                2, "Medianos", 30, "Objetivo 1, Objetivo 2",
//                "Material 1, Material 2, Material 3", "Es la segunda formacion",
//                "Observación 1", per2);
//
//        for3 = new ActividadFormacion(6L, "Formacion", "Formacion 3",
//                LocalDate.of(2024, NOVEMBER, 24), List.of(usr1, usr2, usr3), 3,
//                2, "Mayores", 30, "Objetivo 1, Objetivo 2",
//                "Material 1, Material 2, Material 3", "Es la tercera formacion",
//                "Observación 1", per3);
//    }
//
//    @Bean
//    @Order(3)
//    CommandLineRunner commandLineRunnerUsuario(UsuarioRepository repository) {
//        return args -> {
//            repository.saveAll(List.of(usr1, usr2, usr3));
//        };
//    }
//
//    @Bean
//    @Order(3)
//    CommandLineRunner commandLineRunnerPeriodo(PeriodoRepository repository) {
//        return args -> {
//            repository.saveAll(List.of(per1, per2, per3));
//        };
//    }
//
//    @Bean
//    @Order(3)
//    CommandLineRunner commandLineRunnerActividad(ActividadRepository repository) {
//        return args -> {
//            repository.saveAll(List.of(act1, act2, act3));
//        };
//    }
//
//    @Bean
//    @Order(3)
//    CommandLineRunner commandLineRunnerFormacion(FormacionRepository repository) {
//        return args -> {
//            repository.saveAll(List.of(for1, for2, for3));
//        };
//    }
}
