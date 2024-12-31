package com.tfg.eldest.config;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import com.tfg.eldest.actividadformacion.actividad.ActividadRepository;
import com.tfg.eldest.actividadformacion.formacion.FormacionRepository;
import com.tfg.eldest.periodo.Periodo;
import com.tfg.eldest.periodo.PeriodoRepository;
import com.tfg.eldest.rol.Rol;
import com.tfg.eldest.rol.RolRepository;
import com.tfg.eldest.usuario.Usuario;
import com.tfg.eldest.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.Month.JUNE;
import static java.time.Month.SEPTEMBER;

@Configuration
public class SetUpConfig {
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private FormacionRepository formacionRepository;
    @Autowired
    private PeriodoRepository periodoRepository;

    private List<Usuario> recuperarUsuarios() {
        return usuarioRepository.findAll();
    }

    private List<Periodo> recuperarPeriodos() {
        return periodoRepository.findAll();
    }

    private List<ActividadFormacion> obtenerFormaciones() {
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
                periodoSeleccionado = todosLosPeriodos.get((int) (Math.random() * todosLosPeriodos.size()));
            } else {
                // Usamos el primer periodo de la lista
                periodoSeleccionado = todosLosPeriodos.get(0);
            }

            // Creamos la actividad con los usuarios seleccionados aleatoriamente y el periodo seleccionado
            ActividadFormacion formacion = new ActividadFormacion(
                    "Formacion",
                    "1" + i,
                    "Formacion " + i,
                    LocalDate.of(2024, (int) ((Math.random() * 12) + 1), (int) ((Math.random() * 28) + 1)),  // Fecha aleatoria dentro del año 2024
                    usuariosSeleccionados,
                    (int) (Math.random() * 5) + 1,  // Número aleatorio entre 1 y 5
                    (int) (Math.random() * 5) + 1,  // Número aleatorio entre 1 y 5
                    "Grupo " + ((i % 3) + 1),  // "Pequeños", "Medianos", "Grandes" o algo similar
                    (int) (Math.random() * 40) + 10,  // Número aleatorio entre 10 y 50
                    "Objetivo " + i + "\nObjetivo " + (i + 1),  // Objetivos únicos por actividad
                    "Material " + i + "\nMaterial " + (i + 1),  // Material único por actividad
                    "Descripción de la formacion " + i,  // Descripción personalizada por actividad
                    "Observación " + i,  // Observación personalizada por actividad
                    true,
                    periodoSeleccionado  // Asignamos el periodo seleccionado aleatoriamente o el primero
            );

            formaciones.add(formacion);
        }

        return formaciones;
    }

    private List<ActividadFormacion> obtenerActividades() {
        // Recuperamos la lista de todos los usuarios y períodos
        List<Usuario> todosLosUsuarios = recuperarUsuarios();
        List<Periodo> todosLosPeriodos = recuperarPeriodos();  // Asumimos que hay hasta 20 periodos

        // Generación de 20 actividades con usuarios seleccionados aleatoriamente y periodos aleatorios para algunas
        List<ActividadFormacion> actividades = new ArrayList<>();

        // Creamos 20 actividades
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
                periodoSeleccionado = todosLosPeriodos.get((int) (Math.random() * todosLosPeriodos.size()));
            } else {
                // Usamos el primer periodo de la lista
                periodoSeleccionado = todosLosPeriodos.get(0);
            }

            // Creamos la actividad con los usuarios seleccionados aleatoriamente y el periodo seleccionado
            ActividadFormacion actividad = new ActividadFormacion(
                    "Actividad",
                    "1" + i,
                    "Actividad " + i,
                    LocalDate.of(2024, (int) ((Math.random() * 12) + 1), (int) ((Math.random() * 28) + 1)),  // Fecha aleatoria dentro del año 2024
                    usuariosSeleccionados,
                    (int) (Math.random() * 5) + 1,  // Número aleatorio entre 1 y 5
                    (int) (Math.random() * 5) + 1,  // Número aleatorio entre 1 y 5
                    "Grupo " + ((i % 3) + 1),  // "Pequeños", "Medianos", "Grandes" o algo similar
                    (int) (Math.random() * 40) + 10,  // Número aleatorio entre 10 y 50
                    "Objetivo " + i + "\nObjetivo " + (i + 1),  // Objetivos únicos por actividad
                    "Material " + i + "\nMaterial " + (i + 1),  // Material único por actividad
                    "Descripción de la actividad " + i,  // Descripción personalizada por actividad
                    "Observación " + i,  // Observación personalizada por actividad
                    true,
                    periodoSeleccionado  // Asignamos el periodo seleccionado aleatoriamente o el primero
            );

            actividades.add(actividad);
        }

        return actividades;
    }

    private List<Periodo> obtenerPeriodos() {
        List<Periodo> periodos = new ArrayList<>();

        // Creamos 20 periodos
        for (long i = 1; i <= 5; i++) {
            int year = 2002 + (int) i;

            // Creamos el periodo
            Periodo periodo = new Periodo(
                    "curso " + year + "/" + (year + 1),
                    LocalDate.of(year, SEPTEMBER, ((3 + (int) i) % 30) + 1),
                    LocalDate.of(year + 1, JUNE, ((23 + (int) i) % 30) + 1),
                    true
            );

            periodos.add(periodo);
        }

        return periodos;
    }

    @Bean
    CommandLineRunner commandLineRunnerSetUp() {
        return args -> {
            // Periodos
            List<Periodo> periodos = obtenerPeriodos();
            periodoRepository.saveAll(periodos);

            // Crear roles
            Rol rol1 = new Rol("ROL_ADMIN");
            Rol rol2 = new Rol("ROL_USR");
            Rol rol3 = new Rol("ROL_TEST");
            rolRepository.saveAll(List.of(rol1, rol2, rol3));

            // Crear usuarios
            Usuario usr1 = new Usuario(1L, "Nombre 1", "Apellido 1", "email1@example.com", "Password 1", false, "Voluntario");
            Usuario usr2 = new Usuario(2L, "Nombre 2", "Apellido 2", "email2@example.com", "Password 2", true, "Voluntario");
            Usuario usr3 = new Usuario(3L, "Nombre 3", "Apellido 3", "email3@example.com", "Password 3", true, "Voluntario");
            Usuario usr4 = new Usuario(4L, "Nombre 4", "Apellido 4", "email4@example.com", "Password 4", true, "Socio");
            Usuario usr5 = new Usuario(5L, "Nombre 5", "Apellido 5", "email5@example.com", "Password 5", true, "Socio");

            usr1.setRoles(List.of(rol1));
            usr2.setRoles(List.of(rol2));
            usuarioRepository.saveAll(List.of(usr1, usr2, usr3, usr4, usr5));

            // Crear actividades
            List<ActividadFormacion> actividades = obtenerActividades();
            actividadRepository.saveAll(actividades);

            // Crear formaciones
            List<ActividadFormacion> formaciones = obtenerFormaciones();
            formacionRepository.saveAll(formaciones);
        };
    }
}
