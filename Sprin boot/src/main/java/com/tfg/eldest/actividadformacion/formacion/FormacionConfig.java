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
import java.util.ArrayList;
import java.util.Collections;
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
            // Recuperamos la lista de todos los usuarios y períodos
            List<Usuario> todosLosUsuarios = recuperarUsuarios();
            List<Periodo> todosLosPeriodos = recuperarPeriodos();  // Asumimos que hay hasta 20 periodos

            // Generación de 20 formaciones con usuarios seleccionados aleatoriamente y periodos aleatorios para algunas
            List<ActividadFormacion> formaciones = new ArrayList<>();

            // Creamos 20 formaciones
            for (long i = 1; i <= 50; i++) {
                // Mezclamos la lista de usuarios aleatoriamente para asegurar que la selección sea aleatoria
                List<Usuario> usuariosParaSeleccionar = new ArrayList<>(todosLosUsuarios);  // Copiamos la lista de usuarios
                Collections.shuffle(usuariosParaSeleccionar);  // Mezclamos la lista de usuarios

                // Tomamos los primeros dos usuarios después de mezclar
                List<Usuario> usuariosSeleccionados = usuariosParaSeleccionar.subList(0, 2);  // Tomamos dos usuarios aleatorios

                // Decidimos si esta actividad tendrá un periodo aleatorio o el primer periodo
                Periodo periodoSeleccionado;
                if (Math.random() > 0.5) {  // 50% de probabilidad de cambiar el periodo
                    // Seleccionamos un periodo aleatorio diferente
                    periodoSeleccionado = todosLosPeriodos.get((int)(Math.random() * todosLosPeriodos.size()));
                } else {
                    // Usamos el primer periodo de la lista
                    periodoSeleccionado = todosLosPeriodos.get(0);
                }

                // Creamos la actividad con los usuarios seleccionados aleatoriamente y el periodo seleccionado
                ActividadFormacion formacion = new ActividadFormacion(
                        i+50,
                        "Formacion",
                        "1"+i,
                        "Formacion " + i,
                        LocalDate.of(2024, (int)(Math.random() * 12) + 1, (int)(Math.random() * 28) + 1),  // Fecha aleatoria dentro del año 2024
                        usuariosSeleccionados,
                        usuariosSeleccionados,
                        (int)(Math.random() * 5) + 1,  // Número aleatorio entre 1 y 5
                        (int)(Math.random() * 5) + 1,  // Número aleatorio entre 1 y 5
                        "Grupo " + ((i % 3) + 1),  // "Pequeños", "Medianos", "Grandes" o algo similar
                        (int)(Math.random() * 40) + 10,  // Número aleatorio entre 10 y 50
                        "Objetivo " + i + "\nObjetivo " + (i + 1),  // Objetivos únicos por actividad
                        "Material " + i + "\nMaterial " + (i + 1),  // Material único por actividad
                        "Descripción de la formacion " + i,  // Descripción personalizada por actividad
                        "Observación " + i,  // Observación personalizada por actividad
                        periodoSeleccionado  // Asignamos el periodo seleccionado aleatoriamente o el primero
                );

                formaciones.add(formacion);
            }

            // Guardamos todas las actividades en el repositorio
            repository.saveAll(formaciones);
        };
    }
}
