package com.tfg.eldest.controllers;

import com.tfg.eldest.periodo.Periodo;
import com.tfg.eldest.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/modals")
public class ModalsController {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    @Autowired
    private SessionService sessionService;

    @GetMapping(path = "periodo/cambiar")
    public String cambiarPeriodo(HttpSession session,
                                 @RequestParam Map<String, Object> params,
                                 Model model,
                                 HttpServletRequest request) {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve los periodos
        String apiUrl = this.apiUrl + "/periodos/habilitados";

        // Realizar la solicitud GET a la API de periodos
        ResponseEntity<List<Periodo>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Periodo>>() {
                }
        );

        // Obtener la lista de periodos de la respuesta
        List<Periodo> periodos = response.getBody();

        model.addAttribute("periodos", periodos);
        return "fragments/modals/ModalesPeriodo :: modalCambiarPeriodo";
    }

    @PostMapping(path = "periodo/in_habilitar")
    public String in_hablitarPeriodo(HttpSession session,
                                     @RequestParam Map<String, Object> params,
                                     Model model,
                                     HttpServletRequest request) {
        String id = (String) params.getOrDefault("id", "");
        String nombre = (String) params.getOrDefault("nombre", "");
        String accion = (String) params.getOrDefault("accion", "");
        model.addAttribute("idPeriodo", id);
        model.addAttribute("nombre", nombre);
        model.addAttribute("accion", accion);
        return "fragments/modals/ModalesPeriodo :: modalIn_HabilitarPeriodo";
    }

    @PostMapping(path = "actividad/eliminar")
    public String eliminarActividad(HttpSession session,
                                    @RequestParam Map<String, Object> params,
                                    Model model,
                                    HttpServletRequest request) {
        String id = (String) params.getOrDefault("id", "");
        String titulo = (String) params.getOrDefault("titulo", "");
        model.addAttribute("idActividad", id);
        model.addAttribute("titulo", titulo);
        return "fragments/modals/ModalesActividad :: modalEliminarActividad";
    }

    @PostMapping(path = "formacion/eliminar")
    public String eliminarFormacion(HttpSession session,
                                    @RequestParam Map<String, Object> params,
                                    Model model,
                                    HttpServletRequest request) {
        String id = (String) params.getOrDefault("id", "");
        String titulo = (String) params.getOrDefault("titulo", "");
        model.addAttribute("idFormacion", id);
        model.addAttribute("titulo", titulo);
        return "fragments/modals/ModalesFormacion :: modalEliminarFormacion";
    }

    @PostMapping(path = "voluntario/in_habilitar")
    public String in_hablitarVoluntario(HttpSession session,
                                        @RequestParam Map<String, Object> params,
                                        Model model,
                                        HttpServletRequest request) {
        String id = (String) params.getOrDefault("id", "");
        String nombre = (String) params.getOrDefault("nombre", "");
        String accion = (String) params.getOrDefault("accion", "");
        model.addAttribute("idVoluntario", id);
        model.addAttribute("nombre", nombre);
        model.addAttribute("accion", accion);
        return "fragments/modals/ModalesVoluntarios :: modalIn_HabilitarVoluntario";
    }

    @PostMapping(path = "rol/in_habilitar")
    public String in_hablitarRol(HttpSession session,
                                        @RequestParam Map<String, Object> params,
                                        Model model,
                                        HttpServletRequest request) {
        String id = (String) params.getOrDefault("id", "");
        String nombre = (String) params.getOrDefault("nombre", "");
        String accion = (String) params.getOrDefault("accion", "");
        model.addAttribute("idRol", id);
        model.addAttribute("nombre", nombre);
        model.addAttribute("accion", accion);
        return "fragments/modals/ModalesRoles :: modalIn_HabilitarRol";
    }
}
