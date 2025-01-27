package com.tfg.eldest.backend.config;

import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
import com.tfg.eldest.backend.actividadformacion.actividad.ActividadRepository;
import com.tfg.eldest.backend.actividadformacion.formacion.FormacionRepository;
import com.tfg.eldest.backend.periodo.Periodo;
import com.tfg.eldest.backend.periodo.PeriodoRepository;
import com.tfg.eldest.backend.permiso.Permiso;
import com.tfg.eldest.backend.permiso.PermisoRepository;
import com.tfg.eldest.backend.personalizacion.Personalizacion;
import com.tfg.eldest.backend.personalizacion.PersonalizacionRepository;
import com.tfg.eldest.backend.rol.Rol;
import com.tfg.eldest.backend.rol.RolRepository;
import com.tfg.eldest.backend.usuario.Usuario;
import com.tfg.eldest.backend.usuario.UsuarioRepository;
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
    @Autowired
    private PersonalizacionRepository personalizacionRepository;
    @Autowired
    private PermisoRepository permisoRepository;

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
            ActividadFormacion formacion = new ActividadFormacion("Formacion", "1" + i, "Formacion " + i, LocalDate.of(2024, (int) ((Math.random() * 12) + 1), (int) ((Math.random() * 28) + 1)),  // Fecha aleatoria dentro del año 2024
                    usuariosSeleccionados, (int) (Math.random() * 5) + 1,  // Número aleatorio entre 1 y 5
                    (int) (Math.random() * 5) + 1,  // Número aleatorio entre 1 y 5
                    "Grupo " + ((i % 3) + 1),  // "Pequeños", "Medianos", "Grandes" o algo similar
                    (int) (Math.random() * 40) + 10,  // Número aleatorio entre 10 y 50
                    "Objetivo " + i + "\nObjetivo " + (i + 1),  // Objetivos únicos por actividad
                    "Material " + i + "\nMaterial " + (i + 1),  // Material único por actividad
                    "Descripción de la formacion " + i,  // Descripción personalizada por actividad
                    "Observación " + i,  // Observación personalizada por actividad
                    true, periodoSeleccionado  // Asignamos el periodo seleccionado aleatoriamente o el primero
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
            ActividadFormacion actividad = new ActividadFormacion("Actividad", "1" + i, "Actividad " + i, LocalDate.of(2024, (int) ((Math.random() * 12) + 1), (int) ((Math.random() * 28) + 1)),  // Fecha aleatoria dentro del año 2024
                    usuariosSeleccionados, (int) (Math.random() * 5) + 1,  // Número aleatorio entre 1 y 5
                    (int) (Math.random() * 5) + 1,  // Número aleatorio entre 1 y 5
                    "Grupo " + ((i % 3) + 1),  // "Pequeños", "Medianos", "Grandes" o algo similar
                    (int) (Math.random() * 40) + 10,  // Número aleatorio entre 10 y 50
                    "Objetivo " + i + "\nObjetivo " + (i + 1),  // Objetivos únicos por actividad
                    "Material " + i + "\nMaterial " + (i + 1),  // Material único por actividad
                    "Descripción de la actividad " + i,  // Descripción personalizada por actividad
                    "Observación " + i,  // Observación personalizada por actividad
                    true, periodoSeleccionado  // Asignamos el periodo seleccionado aleatoriamente o el primero
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
            Periodo periodo = new Periodo("curso " + year + "/" + (year + 1), LocalDate.of(year, SEPTEMBER, ((3 + (int) i) % 30) + 1), LocalDate.of(year + 1, JUNE, ((23 + (int) i) % 30) + 1), true);

            periodos.add(periodo);
        }

        return periodos;
    }

    // Descomentar la siguiente función para poblar la base de datos, si ya está poblada dejar comentada.
    /*
    @Bean
    CommandLineRunner commandLineRunnerSetUp() {
        return args -> {
            // Personalización
            Personalizacion personalizacion = new Personalizacion("Organización", "#000000","logoAsociacion.jpg");
            personalizacionRepository.save(personalizacion);

            // Periodos
            List<Periodo> periodos = obtenerPeriodos();
            periodoRepository.saveAll(periodos);

            // Crear permisos
            Permiso C_ACT = new Permiso("C_ACT", "El usuario puede crear actividades");
            Permiso C_FOR = new Permiso("C_FOR", "El usuario puede crear formaciones");
            Permiso V_ACT = new Permiso("V_ACT", "El usuario puede visualizar todas las actividades");
            Permiso V_FOR = new Permiso("V_FOR", "El usuario puede visualizar todas las formaciones");
            Permiso E_M_ACT = new Permiso("E_M_ACT", "El usuario puede editar las actividades en las que es colaborador que no han sido evaluadas");
            Permiso E_M_FOR = new Permiso("E_M_FOR", "El usuario puede editar las formaciones en las que es colaborador");
            Permiso E_ACT = new Permiso("E_ACT", "El usuario puede editar cualquier actividad");
            Permiso E_FOR = new Permiso("E_FOR", "El usuario puede editar cualquier formación");
            Permiso B_M_ACT = new Permiso("B_M_ACT", "El usuario puede borrar las actividades en las que es colaborador");
            Permiso B_M_FOR = new Permiso("B_M_FOR", "El usuario puede borrar las formaciones en las que es colaborador");
            Permiso B_ACT = new Permiso("B_ACT", "El usuario puede borrar cualquier actividad");
            Permiso B_FOR = new Permiso("B_FOR", "El usuario puede borrar cualquier formación");
            Permiso Ev_M_ACT = new Permiso("Ev_M_ACT", "El usuario puede evaluar las actividades en las que es colaborador");
            Permiso Ev_ACT = new Permiso("Ev_ACT", "El usuario puede evaluar cualquier actividad");
            Permiso I_M_ACT = new Permiso("I_M_ACT", "El usuario puede imprimir las actividades en las que es colaborador");
            Permiso I_M_FOR = new Permiso("I_M_FOR", "El usuario puede imprimir las formaciones en las que es colaborador");
            Permiso I_ACT = new Permiso("I_ACT", "El usuario puede imprimir cualquier actividad");
            Permiso I_FOR = new Permiso("I_FOR", "El usuario puede imprimir cualquier formación");

            // Permisos relacionados con pantallas
//            Permiso P_CTRL = new Permiso("P_CTRL", "El usuario tiene acceso al panel de control");
//            Permiso P_COORD = new Permiso("P_COORD", "El usuario tiene acceso al panel de coordinación");

            // Permisos relacionados con las coordinación
            Permiso C_VOL = new Permiso("C_VOL", "El usuario puede crear voluntarios");
            Permiso V_VOL = new Permiso("V_VOL", "El usuario puede visualizar todos los voluntarios");
            Permiso E_VOL = new Permiso("E_VOL", "El usuario puede editar cualquier voluntario");
            Permiso IH_VOL = new Permiso("IH_VOL", "El usuario puede (in)habilitar cualquier voluntario");
            Permiso C_ROL = new Permiso("C_ROL", "El usuario puede crear roles");
            Permiso V_ROL = new Permiso("V_ROL", "El usuario puede visualizar todos los roles");
            Permiso E_ROL = new Permiso("E_ROL", "El usuario puede editar cualquier rol");
            Permiso IH_ROL = new Permiso("IH_ROL", "El usuario puede (in)habilitar cualquier rol");
            Permiso C_PER = new Permiso("C_PER", "El usuario puede crear periodos");
            Permiso V_PER = new Permiso("V_PER", "El usuario puede visualizar todos los periodos");
            Permiso E_PER = new Permiso("E_PER", "El usuario puede editar cualquier periodo");
            Permiso IH_PER = new Permiso("IH_PER", "El usuario puede (in)habilitar cualquier periodo");
            Permiso CUSTOM = new Permiso("CUSTOM", "El usuario puede customizar ciertos atributos de la aplicación");

            permisoRepository.saveAll(List.of(C_ACT, C_FOR, V_ACT, V_FOR, E_M_ACT, E_M_FOR, E_ACT, E_FOR, B_M_ACT, B_M_FOR, B_ACT, B_FOR, Ev_M_ACT, Ev_ACT, I_M_ACT, I_ACT, I_FOR, C_VOL, V_VOL, E_VOL, IH_VOL, C_ROL, V_ROL, E_ROL, IH_ROL, C_PER, V_PER, E_PER, IH_PER, CUSTOM));


            // Crear roles
            Rol rol1 = new Rol("Coordinador", true, List.of(C_ACT, C_FOR, V_ACT, V_FOR, E_ACT, E_FOR, B_ACT, B_FOR, Ev_ACT, I_ACT, C_VOL, V_VOL, E_VOL, IH_VOL, C_ROL, V_ROL, E_ROL, IH_ROL, C_PER, V_PER, E_PER, IH_PER, CUSTOM));
//            Rol rol2 = new Rol("Monitor", List.of(C_ACT,C_FOR,V_ACT,V_FOR,E_M_ACT,E_M_FOR,B_M_ACT,B_M_FOR,Ev_M_ACT,I_M_ACT));
            Rol rol2 = new Rol("Monitor", true, List.of(V_ACT, V_FOR));
            Rol rol3 = new Rol("MonitorAntiguo", false, List.of(V_ACT, V_FOR));
            rolRepository.saveAll(List.of(rol1, rol2, rol3));

            // Crear usuarios
            Usuario usr1 = new Usuario(1L, "Nombre 1", "Apellido 1", "email1@example.com", "Password 1", true, "Voluntario");
            Usuario usr2 = new Usuario(2L, "Nombre 2", "Apellido 2", "email2@example.com", "Password 2", false, "Voluntario");
            Usuario usr3 = new Usuario(3L, "Nombre 3", "Apellido 3", "email3@example.com", "Password 3", true, "Voluntario");
            Usuario usr4 = new Usuario(4L, "Nombre 4", "Apellido 4", "email4@example.com", "Password 4", true, "Socio");
            Usuario usr5 = new Usuario(5L, "Nombre 5", "Apellido 5", "email5@example.com", "Password 5", true, "Socio");

            usr1.setRoles(List.of(rol1));
            usr2.setRoles(List.of(rol2));
            usr3.setRoles(List.of(rol2));
            usuarioRepository.saveAll(List.of(usr1, usr2, usr3, usr4, usr5));

            // Crear actividades
            List<ActividadFormacion> actividades = obtenerActividades();
            actividadRepository.saveAll(actividades);

            // Crear formaciones
            List<ActividadFormacion> formaciones = obtenerFormaciones();
            formacionRepository.saveAll(formaciones);
        };
    }
    */
}
