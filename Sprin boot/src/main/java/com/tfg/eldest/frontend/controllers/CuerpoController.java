package com.tfg.eldest.frontend.controllers;

import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
import com.tfg.eldest.backend.periodo.Periodo;
import com.tfg.eldest.backend.permiso.Permiso;
import com.tfg.eldest.backend.rol.Rol;
import com.tfg.eldest.frontend.services.PermisosService;
import com.tfg.eldest.frontend.services.SessionService;
import com.tfg.eldest.backend.usuario.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Controller
@RequestMapping("/cuerpo")
public class CuerpoController {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PermisosService permisosService;

    private List<Usuario> obtenerVoluntariosHabilitados() {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/usuarios/voluntarios/habilitados";

        // Realizar la solicitud GET a la API de actividades
        ResponseEntity<List<Usuario>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Usuario>>() {
                }
        );

        // Obtener la lista de actividades de la respuesta
        List<Usuario> usuarios = response.getBody();

        return usuarios;
    }

    private List<Rol> obtenerRoles() {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/roles/habilitados";

        // Realizar la solicitud GET a la API de actividades
        ResponseEntity<List<Rol>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Rol>>() {
                }
        );

        // Obtener la lista de actividades de la respuesta
        List<Rol> roles = response.getBody();

        return roles;
    }

    private List<Permiso> obtenerPermisos() {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/permisos";

        // Realizar la solicitud GET a la API de actividades
        ResponseEntity<List<Permiso>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Permiso>>() {
                }
        );

        // Obtener la lista de actividades de la respuesta

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

//            Boolean coordinacion = Boolean.valueOf(sessionService.getCoordinacion(session)); //TODO: Evitar esto
//
//            model.addAttribute("coordinacion", coordinacion);

            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion");
            model.addAttribute("bool_VerCoordinacion", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/actividades");
            model.addAttribute("bool_VerActividades", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/formaciones");
            model.addAttribute("bool_VerFormaciones", tmp);
            return "fragments/cuerpo/PanelDeControl :: content";
        }
        return "Web";
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
        return "Web";
    }

    @PostMapping(path = "coordinacion/voluntarios")
    public String Voluntarios(HttpSession session,
                              @RequestParam Map<String, Object> params,
                              Model model,
                              HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/usuarios/voluntarios";

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<List<Usuario>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Usuario>>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            List<Usuario> voluntarios = response.getBody();

            model.addAttribute("voluntarios", voluntarios);

            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/voluntarios/nuevo");
            model.addAttribute("bool_NuevoVoluntario", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/voluntarios/editar");
            model.addAttribute("bool_EditarVoluntario", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/voluntarios/in_habilitar");
            model.addAttribute("bool_InHabilitarVoluntario", tmp);
            return "fragments/cuerpo/coordinacion/voluntarios/Voluntarios :: content";
        }
        return "Web";
    }

    @PostMapping(path = "coordinacion/voluntarios/nuevo")
    public String NuevoVoluntario(HttpSession session,
                                  @RequestParam Map<String, Object> params,
                                  Model model,
                                  HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/roles/habilitados";

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<List<Rol>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Rol>>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            List<Rol> roles = response.getBody();

            model.addAttribute("roles", roles);
            return "fragments/cuerpo/coordinacion/voluntarios/NuevoVoluntario :: content";
        }
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/usuarios/" + voluntarioID;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<Usuario> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Usuario>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
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
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/usuarios/invertirHabilitado/" + voluntarioID;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<Void> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.PUT,
                    null,
                    Void.class
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Actividad creada exitosamente");
            } else {
                System.out.println("Error al crear la actividad: " + response.getStatusCode());
            }

            return Voluntarios(session, params, model, request);
        }
        return "Web";
    }

    @PostMapping(path = "coordinacion/roles")
    public String Roles(HttpSession session,
                        @RequestParam Map<String, Object> params,
                        Model model,
                        HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/roles";

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<List<Rol>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Rol>>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            List<Rol> roles = response.getBody();

            model.addAttribute("roles", roles);

            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/roles/nuevo");
            model.addAttribute("bool_NuevoRol", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/roles/editar");
            model.addAttribute("bool_EditarRol", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/roles/in_habilitar");
            model.addAttribute("bool_InHabilitarRol", tmp);
            return "fragments/cuerpo/coordinacion/roles/Roles :: content";
        }
        return "Web";
    }

    @PostMapping(path = "coordinacion/roles/nuevo")
    public String NuevoRol(HttpSession session,
                           @RequestParam Map<String, Object> params,
                           Model model,
                           HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/permisos";

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<List<Permiso>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Permiso>>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            List<Permiso> permisos = response.getBody();

            model.addAttribute("permisos", permisos);
            return "fragments/cuerpo/coordinacion/roles/NuevoRol :: content";
        }
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/roles/" + rolID;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<Rol> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Rol>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
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
                tmp.put(permiso.getId().toString(), permiso.getNombre()+" - "+permiso.getDescripcion());
                permisos.put(tmp, encontrado);
            }

            System.out.println(rol);

            model.addAttribute("permisos", permisos);

            return "fragments/cuerpo/coordinacion/roles/InfoRol :: content";
        }
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/roles/" + rolID;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<Rol> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Rol>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
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
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/roles/invertirHabilitado/" + rolID;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<Void> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.PUT,
                    null,
                    Void.class
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Rol modificado exitosamente");
            } else {
                System.out.println("Error al modificar el rol: " + response.getStatusCode());
            }

            return Roles(session, params, model, request);
        }
        return "Web";
    }

    @PostMapping(path = "coordinacion/periodos")
    public String Periodos(HttpSession session,
                           @RequestParam Map<String, Object> params,
                           Model model,
                           HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/periodos";

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<List<Periodo>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Periodo>>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            List<Periodo> periodos = response.getBody();

            model.addAttribute("periodos", periodos);

            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/periodos/nuevo");
            model.addAttribute("bool_NuevoPeriodo", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/periodos/editar");
            model.addAttribute("bool_EditarPeriodo", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/coordinacion/periodos/in_habilitar");
            model.addAttribute("bool_InHabilitarPeriodo", tmp);
            return "fragments/cuerpo/coordinacion/periodos/Periodos :: content";
        }
        return "Web";
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
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/periodos/" + periodoID;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<Periodo> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Periodo>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            Periodo periodo = response.getBody();

            model.addAttribute("periodo", periodo);

            return "fragments/cuerpo/coordinacion/periodos/InfoPeriodo :: content";
        }
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/periodos/invertirHabilitado/" + periodoID;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<Void> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.PUT,
                    null,
                    Void.class
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Periodo modificado exitosamente");
            } else {
                System.out.println("Error al modificar el periodo: " + response.getStatusCode());
            }

            return Periodos(session, params, model, request);
        }
        return "Web";
    }

    @PostMapping(path = "coordinacion/personalizacion")
    public String Personalizacion(HttpSession session,
                                  @RequestParam Map<String, Object> params,
                                  Model model,
                                  HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            return "fragments/cuerpo/coordinacion/personalizacion/Personalizacion :: content";
        }
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/actividades/periodo/" + periodoID;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<List<ActividadFormacion>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ActividadFormacion>>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            List<ActividadFormacion> actividades = response.getBody();

            model.addAttribute("actividades", actividades);

            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/actividades/nueva");
            model.addAttribute("bool_NuevaActividad", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/actividades/editar");
            model.addAttribute("bool_EditarActividad", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/actividades/eliminar");
            model.addAttribute("bool_EliminarActividad", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/pdfs/generar/Actividad");
            model.addAttribute("bool_ImprimirActividad", tmp);
            return "fragments/cuerpo/actividades/Actividades :: content";
        }
        return "Web";
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
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/actividades/" + actividadID;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<ActividadFormacion> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ActividadFormacion>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            ActividadFormacion actividad = response.getBody();

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

            System.out.println(actividad);

            model.addAttribute("encargados", encargados);
            return "fragments/cuerpo/actividades/InfoActividad :: content";
        }
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/actividades/eliminar/" + actividadID;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<Void> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.PUT,
                    null,
                    Void.class
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Actividad creada exitosamente");
            } else {
                System.out.println("Error al crear la actividad: " + response.getStatusCode());
            }

            return Actividades(session, params, model, request);
        }
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/formaciones/periodo/" + periodoID;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<List<ActividadFormacion>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ActividadFormacion>>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            List<ActividadFormacion> formaciones = response.getBody();

            model.addAttribute("formaciones", formaciones);

            Boolean tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/formaciones/nueva");
            model.addAttribute("bool_NuevaFormacion", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/formaciones/editar");
            model.addAttribute("bool_EditarFormacion", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/cuerpo/formaciones/eliminar");
            model.addAttribute("bool_EliminarFormacion", tmp);
            tmp = permisosService.comprobarPermisos(usuarioId, "/pdfs/generar/formaciones");
            model.addAttribute("bool_ImprimirFormacion", tmp);
            return "fragments/cuerpo/formaciones/Formaciones :: content";
        }
        return "Web";
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
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las formaciones
            String apiUrl = this.apiUrl + "/formaciones/" + formacionID;

            // Realizar la solicitud GET a la API de formaciones
            ResponseEntity<ActividadFormacion> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ActividadFormacion>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
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

            System.out.println(formacion);

            model.addAttribute("encargados", encargados);
            return "fragments/cuerpo/formaciones/InfoFormacion :: content";
        }
        return "Web";
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

            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las formaciones
            String apiUrl = this.apiUrl + "/formaciones/eliminar/" + formacionID;

            // Realizar la solicitud GET a la API de formaciones
            ResponseEntity<Void> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.PUT,
                    null,
                    Void.class
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Formación creada exitosamente");
            } else {
                System.out.println("Error al crear la formación: " + response.getStatusCode());
            }

            return Formaciones(session, params, model, request);
        }
        return "Web";
    }

}
