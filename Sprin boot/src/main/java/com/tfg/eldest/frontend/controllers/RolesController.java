package com.tfg.eldest.frontend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tfg.eldest.backend.permiso.Permiso;
import com.tfg.eldest.backend.personalizacion.Personalizacion;
import com.tfg.eldest.backend.rol.Rol;
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
@RequestMapping("/coordinacion/roles")
public class RolesController {
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

    private List<Permiso> obtenerPermisos(@RequestParam Map<String, Object> params) {
        // Llamada a la api
        ResponseEntity<List<Permiso>> response = apiTemplateService.llamadaApi(
                "/permisos",
                "GET",
                "Permisos",
                null
        );

        List<Permiso> permisos = response.getBody();

        List<Permiso> seleccionados = new ArrayList<>();
        for (Permiso permiso : permisos) {
            String id = (String) params.get("permiso " + permiso.getId());
            if (id != null && !id.isEmpty()) {
                seleccionados.add(permiso);
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
            Rol nuevoRol = new Rol(
                    (String) params.get("nombre"),
                    obtenerPermisos(params)
            );

            // Llamada a la api
            ResponseEntity<Rol> response = apiTemplateService.llamadaApi(
                    "/roles/crear",
                    "POST",
                    "Void",
                    nuevoRol
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Rol creado exitosamente");
            } else {
                System.out.println("Error al crear el rol: " + response.getStatusCode());
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
            Rol nuevoRol = new Rol(
                    (String) params.get("nombre"),
                    obtenerPermisos(params)
            );

            // Llamada a la api
            ResponseEntity<Rol> response = apiTemplateService.llamadaApi(
                    "/roles/guardar/" + params.get("id"),
                    "PUT",
                    "Void",
                    nuevoRol
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Rol editado exitosamente");
            } else {
                System.out.println("Error al editar el rol: " + response.getStatusCode());
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
}
