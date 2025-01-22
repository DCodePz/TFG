package com.tfg.eldest.frontend.controllers;

import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
import com.tfg.eldest.backend.periodo.Periodo;
import com.tfg.eldest.backend.permiso.Permiso;
import com.tfg.eldest.backend.personalizacion.Personalizacion;
import com.tfg.eldest.backend.rol.Rol;
import com.tfg.eldest.backend.usuario.Usuario;
import com.tfg.eldest.frontend.services.ApiTemplateService;
import com.tfg.eldest.frontend.services.PermisosService;
import com.tfg.eldest.frontend.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Controller
@RequestMapping("/cuerpo")
public class CuerpoController {
    // -- Servicios --
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermisosService permisosService;
    @Autowired
    private ApiTemplateService apiTemplateService;
    // ---------------

    @Autowired
    private HomeController homeController;

    private List<Usuario> obtenerVoluntariosHabilitados() {
        // Llamada a la api
        ResponseEntity<List<Usuario>> response = apiTemplateService.llamadaApi(
                "/usuarios/voluntarios/habilitados",
                "GET",
                "Usuarios",
                null
        );

        return response.getBody();
    }

    private List<Rol> obtenerRoles() {
        // Llamada a la api
        ResponseEntity<List<Rol>> response = apiTemplateService.llamadaApi(
                "/roles/habilitados",
                "GET",
                "Roles",
                null
        );

        return response.getBody();
    }

    private List<Permiso> obtenerPermisos() {
        // Llamada a la api
        ResponseEntity<List<Permiso>> response = apiTemplateService.llamadaApi(
                "/permisos",
                "GET",
                "Permisos",
                null
        );

        return response.getBody();
    }

    //    PANEL DE CONTROL
    @PostMapping(path = "paneldecontrol")
    public String PanelDeControl(HttpSession session,
                                 @RequestParam Map<String, Object> params,
                                 Model model,
                                 HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion");
            model.addAttribute("bool_VerCoordinacion", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/actividades");
            model.addAttribute("bool_VerActividades", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/formaciones");
            model.addAttribute("bool_VerFormaciones", tmp);
            return "fragments/cuerpo/PanelDeControl :: content";
        }
        return homeController.home(session,params,model,request);
    }

    //    COORDINACIÓN
    @PostMapping(path = "coordinacion")
    public String Coordinacion(HttpSession session,
                               @RequestParam Map<String, Object> params,
                               Model model,
                               HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/voluntarios");
            model.addAttribute("bool_VerVoluntarios", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/roles");
            model.addAttribute("bool_VerRoles", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/periodos");
            model.addAttribute("bool_VerPeriodos", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/personalizacion");
            model.addAttribute("bool_VerPersonalizacion", tmp);
            return "fragments/cuerpo/coordinacion/Coordinacion :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/voluntarios")
    public String Voluntarios(HttpSession session,
                              @RequestParam Map<String, Object> params,
                              Model model,
                              HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Llamada a la api
            ResponseEntity<List<Usuario>> response = apiTemplateService.llamadaApi(
                    "/usuarios/voluntarios",
                    "GET",
                    "Usuarios",
                    null
            );

            model.addAttribute("voluntarios", response.getBody());

            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/voluntarios/nuevo");
            model.addAttribute("bool_NuevoVoluntario", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/voluntarios/editar");
            model.addAttribute("bool_EditarVoluntario", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/voluntarios/in_habilitar");
            model.addAttribute("bool_InHabilitarVoluntario", tmp);
            return "fragments/cuerpo/coordinacion/voluntarios/Voluntarios :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/voluntarios/nuevo")
    public String NuevoVoluntario(HttpSession session,
                                  @RequestParam Map<String, Object> params,
                                  Model model,
                                  HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Llamada a la api
            ResponseEntity<List<Rol>> response = apiTemplateService.llamadaApi(
                    "/roles/habilitados",
                    "GET",
                    "Roles",
                    null
            );

            model.addAttribute("roles", response.getBody());
            return "fragments/cuerpo/coordinacion/voluntarios/NuevoVoluntario :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/voluntarios/editar")
    public String EditarVoluntario(HttpSession session,
                                   @RequestParam Map<String, Object> params,
                                   Model model,
                                   HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String voluntarioID = (String) params.getOrDefault("id", null);

            // Llamada a la api
            ResponseEntity<Usuario> response = apiTemplateService.llamadaApi(
                    "/usuarios/" + voluntarioID,
                    "GET",
                    "Usuario",
                    null
            );

            Usuario voluntario = response.getBody();
            model.addAttribute("voluntario", voluntario);

            // Procesamos los roles
            List<Rol> todosRoles = obtenerRoles();
            Map<Map<String, String>, Boolean> roles = new HashMap<>();

            for (Rol rol : todosRoles) {
                Boolean encontrado = FALSE;
                for (Rol buscar : voluntario.getRoles()) {
                    if (rol.getId().equals(buscar.getId())) {
                        encontrado = TRUE;
                        break;
                    }
                }

                Map<String, String> tmp = new HashMap<>();
                tmp.put(rol.getId().toString(), rol.getNombre());
                roles.put(tmp, encontrado);
            }

            System.out.println(voluntario);
            System.out.println(roles);

            model.addAttribute("roles", roles);
            return "fragments/cuerpo/coordinacion/voluntarios/InfoVoluntario :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/voluntarios/in_habilitar")
    public String in_habilitarVoluntario(HttpSession session,
                                         @RequestParam Map<String, Object> params,
                                         Model model,
                                         HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String voluntarioID = (String) params.getOrDefault("id", null);

            // Llamada a la api
            ResponseEntity<Void> response = apiTemplateService.llamadaApi(
                    "/usuarios/invertirHabilitado/" + voluntarioID,
                    "PUT",
                    "Void",
                    null
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Actividad creada exitosamente");
            } else {
                System.out.println("Error al crear la actividad: " + response.getStatusCode());
            }

            return Voluntarios(session, params, model, request);
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/roles")
    public String Roles(HttpSession session,
                        @RequestParam Map<String, Object> params,
                        Model model,
                        HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Llamada a la api
            ResponseEntity<List<Rol>> response = apiTemplateService.llamadaApi(
                    "/roles",
                    "GET",
                    "Roles",
                    null
            );

            model.addAttribute("roles", response.getBody());

            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/roles/nuevo");
            model.addAttribute("bool_NuevoRol", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/roles/editar");
            model.addAttribute("bool_EditarRol", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/roles/in_habilitar");
            model.addAttribute("bool_InHabilitarRol", tmp);
            return "fragments/cuerpo/coordinacion/roles/Roles :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/roles/nuevo")
    public String NuevoRol(HttpSession session,
                           @RequestParam Map<String, Object> params,
                           Model model,
                           HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Llamada a la api
            ResponseEntity<List<Permiso>> response = apiTemplateService.llamadaApi(
                    "/permisos",
                    "GET",
                    "Permisos",
                    null
            );

            model.addAttribute("permisos", response.getBody());
            return "fragments/cuerpo/coordinacion/roles/NuevoRol :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/roles/editar")
    public String EditarRol(HttpSession session,
                            @RequestParam Map<String, Object> params,
                            Model model,
                            HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String rolID = (String) params.getOrDefault("id", null);

            // Llamada a la api
            ResponseEntity<Rol> response = apiTemplateService.llamadaApi(
                    "/roles/" + rolID,
                    "GET",
                    "Rol",
                    null
            );

            Rol rol = response.getBody();
            model.addAttribute("rol", rol);

            // Procesamos los permisos
            List<Permiso> todosPermisos = obtenerPermisos();
            Map<Map<String, String>, Boolean> permisos = new HashMap<>();

            for (Permiso permiso : todosPermisos) {
                Boolean encontrado = FALSE;
                for (Permiso buscar : rol.getPermisos()) {
                    if (permiso.getId().equals(buscar.getId())) {
                        encontrado = TRUE;
                        break;
                    }
                }

                Map<String, String> tmp = new HashMap<>();
                tmp.put(permiso.getId().toString(), permiso.getNombre() + " - " + permiso.getDescripcion());
                permisos.put(tmp, encontrado);
            }

            System.out.println(rol);

            model.addAttribute("permisos", permisos);

            return "fragments/cuerpo/coordinacion/roles/InfoRol :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/roles/ver")
    public String VerRol(HttpSession session,
                         @RequestParam Map<String, Object> params,
                         Model model,
                         HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String rolID = (String) params.getOrDefault("id", null);

            // Llamada a la api
            ResponseEntity<Rol> response = apiTemplateService.llamadaApi(
                    "/roles/" + rolID,
                    "GET",
                    "Rol",
                    null
            );

            Rol rol = response.getBody();
            model.addAttribute("rol", rol);

            // Procesamos los permisos
            List<Permiso> todosPermisos = obtenerPermisos();
            Map<Map<String, String>, Boolean> permisos = new HashMap<>();

            for (Permiso permiso : todosPermisos) {
                Boolean encontrado = FALSE;
                for (Permiso buscar : rol.getPermisos()) {
                    if (permiso.getId().equals(buscar.getId())) {
                        encontrado = TRUE;
                        break;
                    }
                }

                if (encontrado) {
                    Map<String, String> tmp = new HashMap<>();
                    tmp.put(permiso.getNombre(), permiso.getNombre() + " - " + permiso.getDescripcion());
                    permisos.put(tmp, encontrado);
                }
            }

            System.out.println(rol);

            model.addAttribute("permisos", permisos);

            return "fragments/cuerpo/coordinacion/roles/VerRol :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/roles/in_habilitar")
    public String in_habilitarRol(HttpSession session,
                                  @RequestParam Map<String, Object> params,
                                  Model model,
                                  HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String rolID = (String) params.getOrDefault("id", null);

            // Llamada a la api
            ResponseEntity<Void> response = apiTemplateService.llamadaApi(
                    "/roles/invertirHabilitado/" + rolID,
                    "PUT",
                    "Void",
                    null
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Rol modificado exitosamente");
            } else {
                System.out.println("Error al modificar el rol: " + response.getStatusCode());
            }

            return Roles(session, params, model, request);
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/periodos")
    public String Periodos(HttpSession session,
                           @RequestParam Map<String, Object> params,
                           Model model,
                           HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Llamada a la api
            ResponseEntity<List<Periodo>> response = apiTemplateService.llamadaApi(
                    "/periodos",
                    "GET",
                    "Periodos",
                    null
            );

            model.addAttribute("periodos", response.getBody());

            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/periodos/nuevo");
            model.addAttribute("bool_NuevoPeriodo", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/periodos/editar");
            model.addAttribute("bool_EditarPeriodo", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/periodos/in_habilitar");
            model.addAttribute("bool_InHabilitarPeriodo", tmp);
            return "fragments/cuerpo/coordinacion/periodos/Periodos :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/periodos/nuevo")
    public String NuevoPeriodo(HttpSession session,
                               @RequestParam Map<String, Object> params,
                               Model model,
                               HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            return "fragments/cuerpo/coordinacion/periodos/NuevoPeriodo :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/periodos/editar")
    public String EditarPeriodo(HttpSession session,
                                @RequestParam Map<String, Object> params,
                                Model model,
                                HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String periodoID = (String) params.getOrDefault("id", null);

            // Llamada a la api
            ResponseEntity<Periodo> response = apiTemplateService.llamadaApi(
                    "/periodos/" + periodoID,
                    "GET",
                    "Periodo",
                    null
            );

            model.addAttribute("periodo", response.getBody());

            return "fragments/cuerpo/coordinacion/periodos/InfoPeriodo :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/periodos/in_habilitar")
    public String in_habilitarPeriodo(HttpSession session,
                                      @RequestParam Map<String, Object> params,
                                      Model model,
                                      HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String periodoID = (String) params.getOrDefault("id", null);

            // Llamada a la api
            ResponseEntity<Void> response = apiTemplateService.llamadaApi(
                    "/periodos/invertirHabilitado/" + periodoID,
                    "PUT",
                    "Void",
                    null
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Periodo modificado exitosamente");
            } else {
                System.out.println("Error al modificar el periodo: " + response.getStatusCode());
            }

            return Periodos(session, params, model, request);
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "coordinacion/personalizacion")
    public String Personalizacion(HttpSession session,
                                  @RequestParam Map<String, Object> params,
                                  Model model,
                                  HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Llamada a la api
            ResponseEntity<Personalizacion> response = apiTemplateService.llamadaApi(
                    "/personalizacion",
                    "GET",
                    "Personalizacion",
                    null
            );
            Personalizacion personalizacion = response.getBody();

            model.addAttribute("color", personalizacion.getColor());
            return "fragments/cuerpo/coordinacion/personalizacion/Personalizacion :: content";
        }
        return homeController.home(session,params,model,request);
    }

    //    ACTIVIDADES
    @PostMapping(path = "actividades")
    public String Actividades(HttpSession session,
                              @RequestParam Map<String, Object> params,
                              Model model,
                              HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String periodoID = sessionService.getPeriodoID(session);

            // Llamada a la api
            ResponseEntity<List<ActividadFormacion>> response = apiTemplateService.llamadaApi(
                    "/actividades/periodo/" + periodoID,
                    "GET",
                    "ActividadesFormaciones",
                    null
            );

            model.addAttribute("actividades", response.getBody());

            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/actividades/nueva");
            model.addAttribute("bool_NuevaActividad", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/actividades/editar");
            model.addAttribute("bool_EditarActividad", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/actividades/eliminar");
            model.addAttribute("bool_EliminarActividad", tmp);
//            tmp = permisosService.comprobarPermisos(usuarioId, "/pdfs/generar/Actividad");
//            model.addAttribute("bool_ImprimirActividad", tmp);
            model.addAttribute("bool_ImprimirActividad", false);
            return "fragments/cuerpo/actividades/Actividades :: content";
        }
        return homeController.home(session,params,model,request);
    }

    // TODO: Filtar el rol de usuario
    @PostMapping(path = "actividades/nueva")
    public String NuevaActividad(HttpSession session,
                                 @RequestParam Map<String, Object> params,
                                 Model model,
                                 HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            model.addAttribute("encargados", obtenerVoluntariosHabilitados());
            return "fragments/cuerpo/actividades/NuevaActividad :: content";
        }
        return homeController.home(session,params,model,request);
    }


    @PostMapping(path = "actividades/editar")
    public String EditarActividad(HttpSession session,
                                  @RequestParam Map<String, Object> params,
                                  Model model,
                                  HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String actividadID = (String) params.getOrDefault("id", null);

            // Llamada a la api
            ResponseEntity<ActividadFormacion> response = apiTemplateService.llamadaApi(
                    "/actividades/" + actividadID,
                    "GET",
                    "ActividadFormacion",
                    null
            );

            System.out.println(response.getBody());
            ActividadFormacion actividad = response.getBody();
            System.out.println(actividad);

            model.addAttribute("actividad", actividad);

            // Procesamos la edades
            String[] partes = actividad.getGrupo_edad().split(",");
            Boolean ppChecked = FALSE, pgChecked = FALSE, mdChecked = FALSE, myChecked = FALSE;
            for (String parte : partes) {
                switch (parte) {
                    case "Pequeños Pequeños" -> ppChecked = TRUE;
                    case "Pequeños Grandes" -> pgChecked = TRUE;
                    case "Medianos" -> mdChecked = TRUE;
                    case "Mayores" -> myChecked = TRUE;
                }
            }
            model.addAttribute("ppChecked", ppChecked);
            model.addAttribute("pgChecked", pgChecked);
            model.addAttribute("mdChecked", mdChecked);
            model.addAttribute("myChecked", myChecked);

            // Procesamos los encargados
            List<Usuario> todosUsuarios = obtenerVoluntariosHabilitados();
            Map<String, Boolean> encargados = new HashMap<>();

            for (Usuario usuario : todosUsuarios) {
                Boolean encontrado = FALSE;
                for (Usuario encargado : actividad.getEncargados()) {
                    if (usuario.getId().equals(encargado.getId())) {
                        encontrado = TRUE;
                        break;
                    }
                }

                encargados.put(usuario.getId().toString(), encontrado);
            }

            model.addAttribute("encargados", encargados);
            return "fragments/cuerpo/actividades/InfoActividad :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "actividades/eliminar")
    public String EliminarActividad(HttpSession session,
                                    @RequestParam Map<String, Object> params,
                                    Model model,
                                    HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String actividadID = (String) params.getOrDefault("id", null);

            // Llamada a la api
            ResponseEntity<Void> response = apiTemplateService.llamadaApi(
                    "/actividades/eliminar/" + actividadID,
                    "PUT",
                    "Void",
                    null
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Actividad creada exitosamente");
            } else {
                System.out.println("Error al crear la actividad: " + response.getStatusCode());
            }

            return Actividades(session, params, model, request);
        }
        return homeController.home(session,params,model,request);
    }

    //    FORMACIONES
    @PostMapping(path = "formaciones")
    public String Formaciones(HttpSession session,
                              @RequestParam Map<String, Object> params,
                              Model model,
                              HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String periodoID = sessionService.getPeriodoID(session);

            // Llamada a la api
            ResponseEntity<List<ActividadFormacion>> response = apiTemplateService.llamadaApi(
                    "/formaciones/periodo/" + periodoID,
                    "GET",
                    "ActividadesFormaciones",
                    null
            );

            model.addAttribute("formaciones", response.getBody());

            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/formaciones/nueva");
            model.addAttribute("bool_NuevaFormacion", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/formaciones/editar");
            model.addAttribute("bool_EditarFormacion", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/formaciones/eliminar");
            model.addAttribute("bool_EliminarFormacion", tmp);
//            tmp = permisosService.comprobarPermisos(usuarioId, "/pdfs/generar/Formacion");
//            model.addAttribute("bool_ImprimirFormacion", tmp);
            model.addAttribute("bool_ImprimirFormacion", false);
            return "fragments/cuerpo/formaciones/Formaciones :: content";
        }
        return homeController.home(session,params,model,request);
    }

    // TODO: Filtar el rol de usuario
    @PostMapping(path = "formaciones/nueva")
    public String NuevaFormacion(HttpSession session,
                                 @RequestParam Map<String, Object> params,
                                 Model model,
                                 HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            model.addAttribute("encargados", obtenerVoluntariosHabilitados());
            return "fragments/cuerpo/formaciones/NuevaFormacion :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "formaciones/editar")
    public String EditarFormacion(HttpSession session,
                                  @RequestParam Map<String, Object> params,
                                  Model model,
                                  HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String formacionID = (String) params.getOrDefault("id", null);

            // Llamada a la api
            ResponseEntity<ActividadFormacion> response = apiTemplateService.llamadaApi(
                    "/formaciones/" + formacionID,
                    "GET",
                    "ActividadFormacion",
                    null
            );


            ActividadFormacion formacion = response.getBody();
            model.addAttribute("formacion", formacion);

            // Procesamos la edades
            String[] partes = formacion.getGrupo_edad().split(",");
            Boolean ppChecked = FALSE, pgChecked = FALSE, mdChecked = FALSE, myChecked = FALSE;
            for (String parte : partes) {
                switch (parte) {
                    case "Pequeños Pequeños" -> ppChecked = TRUE;
                    case "Pequeños Grandes" -> pgChecked = TRUE;
                    case "Medianos" -> mdChecked = TRUE;
                    case "Mayores" -> myChecked = TRUE;
                }
            }
            model.addAttribute("ppChecked", ppChecked);
            model.addAttribute("pgChecked", pgChecked);
            model.addAttribute("mdChecked", mdChecked);
            model.addAttribute("myChecked", myChecked);

            // Procesamos los encargados
            List<Usuario> todosUsuarios = obtenerVoluntariosHabilitados();
            Map<String, Boolean> encargados = new HashMap<>();

            for (Usuario usuario : todosUsuarios) {
                Boolean encontrado = FALSE;
                for (Usuario encargado : formacion.getEncargados()) {
                    if (usuario.getId().equals(encargado.getId())) {
                        encontrado = TRUE;
                        break;
                    }
                }

                encargados.put(usuario.getId().toString(), encontrado);
            }

            model.addAttribute("encargados", encargados);
            return "fragments/cuerpo/formaciones/InfoFormacion :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "formaciones/eliminar")
    public String EliminarFormacion(HttpSession session,
                                    @RequestParam Map<String, Object> params,
                                    Model model,
                                    HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String formacionID = (String) params.getOrDefault("id", null);

            // Llamada a la api
            ResponseEntity<Void> response = apiTemplateService.llamadaApi(
                    "/formaciones/eliminar/" + formacionID,
                    "PUT",
                    "Void",
                    null
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Formación creada exitosamente");
            } else {
                System.out.println("Error al crear la formación: " + response.getStatusCode());
            }

            return Formaciones(session, params, model, request);
        }
        return homeController.home(session,params,model,request);
    }

}
