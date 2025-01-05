package com.tfg.eldest.frontend.controllers;

import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
import com.tfg.eldest.backend.periodo.Periodo;
import com.tfg.eldest.frontend.services.ApiTemplateService;
import com.tfg.eldest.frontend.services.PermisosService;
import com.tfg.eldest.frontend.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/modals")
public class ModalsController {
    // -- Servicios --
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermisosService permisosService;
    @Autowired
    private ApiTemplateService apiTemplateService;
    // ---------------

    @GetMapping(path = "periodo/cambiar")
    public String cambiarPeriodo(HttpSession session,
                                 @RequestParam Map<String, Object> params,
                                 Model model,
                                 HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Llamada a la api
            ResponseEntity<List<Periodo>> response = apiTemplateService.llamadaApi(
                    "/periodos/habilitados",
                    "GET",
                    "Periodos",
                    null
            );

            model.addAttribute("periodos", response.getBody());
            return "fragments/modals/ModalesPeriodo :: modalCambiarPeriodo";
        }
        return "Web";
    }

    @PostMapping(path = "periodo/in_habilitar")
    public String in_hablitarPeriodo(HttpSession session,
                                     @RequestParam Map<String, Object> params,
                                     Model model,
                                     HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String id = (String) params.getOrDefault("id", "");
            String nombre = (String) params.getOrDefault("nombre", "");
            String accion = (String) params.getOrDefault("accion", "");
            model.addAttribute("idPeriodo", id);
            model.addAttribute("nombre", nombre);
            model.addAttribute("accion", accion);
            return "fragments/modals/ModalesPeriodo :: modalIn_HabilitarPeriodo";
        }
        return "Web";
    }

    @PostMapping(path = "actividad/eliminar")
    public String eliminarActividad(HttpSession session,
                                    @RequestParam Map<String, Object> params,
                                    Model model,
                                    HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String id = (String) params.getOrDefault("id", "");
            String titulo = (String) params.getOrDefault("titulo", "");
            model.addAttribute("idActividad", id);
            model.addAttribute("titulo", titulo);
            return "fragments/modals/ModalesActividad :: modalEliminarActividad";
        }
        return "Web";
    }

    @PostMapping(path = "formacion/eliminar")
    public String eliminarFormacion(HttpSession session,
                                    @RequestParam Map<String, Object> params,
                                    Model model,
                                    HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String id = (String) params.getOrDefault("id", "");
            String titulo = (String) params.getOrDefault("titulo", "");
            model.addAttribute("idFormacion", id);
            model.addAttribute("titulo", titulo);
            return "fragments/modals/ModalesFormacion :: modalEliminarFormacion";
        }
        return "Web";
    }

    @PostMapping(path = "voluntario/in_habilitar")
    public String in_hablitarVoluntario(HttpSession session,
                                        @RequestParam Map<String, Object> params,
                                        Model model,
                                        HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String id = (String) params.getOrDefault("id", "");
            String nombre = (String) params.getOrDefault("nombre", "");
            String accion = (String) params.getOrDefault("accion", "");
            model.addAttribute("idVoluntario", id);
            model.addAttribute("nombre", nombre);
            model.addAttribute("accion", accion);
            return "fragments/modals/ModalesVoluntarios :: modalIn_HabilitarVoluntario";
        }
        return "Web";
    }

    @PostMapping(path = "rol/in_habilitar")
    public String in_hablitarRol(HttpSession session,
                                        @RequestParam Map<String, Object> params,
                                        Model model,
                                        HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String id = (String) params.getOrDefault("id", "");
            String nombre = (String) params.getOrDefault("nombre", "");
            String accion = (String) params.getOrDefault("accion", "");
            model.addAttribute("idRol", id);
            model.addAttribute("nombre", nombre);
            model.addAttribute("accion", accion);
            return "fragments/modals/ModalesRoles :: modalIn_HabilitarRol";
        }
        return "Web";
    }
}
