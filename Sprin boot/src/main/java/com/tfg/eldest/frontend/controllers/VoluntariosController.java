package com.tfg.eldest.frontend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/coordinacion/voluntarios")
public class VoluntariosController {
    // -- Servicios --
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermisosService permisosService;
    @Autowired
    private ApiTemplateService apiTemplateService;
    // ---------------

    @Autowired
    private CuerpoController cuerpoController;
    @Autowired
    private HomeController homeController;

    private List<Rol> obtenerRoles(@RequestParam Map<String, Object> params) {
        // Llamada a la api
        ResponseEntity<List<Rol>> response = apiTemplateService.llamadaApi(
                "/roles",
                "GET",
                "Roles",
                null
        );

        List<Rol> roles = response.getBody();

        List<Rol> seleccionados = new ArrayList<>();
        for (Rol rol : roles) {
            String id = (String) params.get("rol " + rol.getId());
            if (id != null && !id.isEmpty()) {
                seleccionados.add(rol);
            }
        }
        return seleccionados;
    }

    @PostMapping(path = "crear")
    public String Crear(HttpSession session,
                        @RequestParam Map<String, Object> params,
                        Model model,
                        HttpServletRequest request) throws JsonProcessingException {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Crear el objeto ActividadFormacion que se enviará en el cuerpo de la solicitud
            Usuario nuevoVoluntario = new Usuario(
                    Long.parseLong((String) params.get("id")),
                    (String) params.get("nombre"),
                    (String) params.get("apellidos"),
                    (String) params.get("email"),
                    (String) params.get("password"),
                    "Voluntario",
                    obtenerRoles(params)
            );

            // Llamada a la api
            ResponseEntity<Usuario> response = apiTemplateService.llamadaApi(
                    "/usuarios/crear",
                    "POST",
                    "Void",
                    nuevoVoluntario
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Voluntario creado exitosamente");
            } else {
                System.out.println("Error al crear el voluntario: " + response.getStatusCode());
            }

            // Llamada a la api
            ResponseEntity<Personalizacion> response2 = apiTemplateService.llamadaApi(
                    "/personalizacion",
                    "GET",
                    "Personalizacion",
                    null
            );
            Personalizacion personalizacion = response2.getBody();

            String url = (String) params.getOrDefault("url", "paneldecontrol");
            String titulo = (String) params.getOrDefault("tit", "Título");

            model.addAttribute("url", url);
            model.addAttribute("titulo", titulo);
            model.addAttribute("org", personalizacion.getNombre());
            return "fragments/Cabecera :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "guardar")
    public String Guardar(HttpSession session,
                          @RequestParam Map<String, Object> params,
                          Model model,
                          HttpServletRequest request) throws JsonProcessingException {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Crear el objeto ActividadFormacion que se enviará en el cuerpo de la solicitud
            Usuario nuevoVoluntario = new Usuario(
                    Long.parseLong((String) params.get("id")),
                    (String) params.get("nombre"),
                    (String) params.get("apellidos"),
                    (String) params.get("email"),
                    (String) params.get("password"),
                    "Voluntario",
                    obtenerRoles(params)
            );

            // Llamada a la api
            ResponseEntity<Usuario> response = apiTemplateService.llamadaApi(
                    "/usuarios/guardar/" + params.get("id"),
                    "PUT",
                    "Void",
                    nuevoVoluntario
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Voluntario editado exitosamente");
            } else {
                System.out.println("Error al editar el voluntarioi: " + response.getStatusCode());
            }

            // Llamada a la api
            ResponseEntity<Personalizacion> response2 = apiTemplateService.llamadaApi(
                    "/personalizacion",
                    "GET",
                    "Personalizacion",
                    null
            );
            Personalizacion personalizacion = response2.getBody();

            String url = (String) params.getOrDefault("url", "paneldecontrol");
            String titulo = (String) params.getOrDefault("tit", "Título");

            model.addAttribute("url", url);
            model.addAttribute("titulo", titulo);
            model.addAttribute("org", personalizacion.getNombre());
            return "fragments/Cabecera :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "buscar")
    public String buscarVoluntarios(HttpSession session,
                                    @RequestParam Map<String, Object> params,
                                    Model model,
                                    HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String query = (String) params.getOrDefault("query", "");

            String fragment = "";
            if (query.isEmpty()) {
                fragment = cuerpoController.Voluntarios(session, params, model, request);
            } else {
                // Llamada a la api
                ResponseEntity<List<Usuario>> response = apiTemplateService.llamadaApi(
                        "/usuarios/voluntarios/buscar/" + query,
                        "GET",
                        "Usuarios",
                        null
                );

                model.addAttribute("voluntarios", response.getBody());

                fragment = "fragments/cuerpo/coordinacion/voluntarios/Voluntarios :: content";
            }

            return fragment;
        }
        return homeController.home(session,params,model,request);
    }
}
