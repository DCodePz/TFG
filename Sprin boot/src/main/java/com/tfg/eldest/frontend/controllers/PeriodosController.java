package com.tfg.eldest.frontend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tfg.eldest.backend.periodo.Periodo;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/coordinacion/periodos")
public class PeriodosController {
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

    @PostMapping(path = "crear")
    public String Crear(HttpSession session,
                        @RequestParam Map<String, Object> params,
                        Model model,
                        HttpServletRequest request) throws JsonProcessingException {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Crear el objeto ActividadFormacion que se enviará en el cuerpo de la solicitud
            Periodo nuevoPeriodo = new Periodo(
                    (String) params.get("nombre"),
                    LocalDate.parse((String) params.get("fechaInicio")),
                    LocalDate.parse((String) params.get("fechaFin"))
            );

            // Llamada a la api
            ResponseEntity<Periodo> response = apiTemplateService.llamadaApi(
                    "/periodos/crear",
                    "POST",
                    "Void",
                    nuevoPeriodo
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Periodo creado exitosamente");
            } else {
                System.out.println("Error al crear el periodo: " + response.getStatusCode());
            }

            String url = (String) params.getOrDefault("url", "paneldecontrol");
            String titulo = (String) params.getOrDefault("tit", "Título");

            model.addAttribute("url", url);
            model.addAttribute("titulo", titulo);
            model.addAttribute("org", sessionService.getOrg(session));
            return "fragments/Cabecera :: content";
        }
        return "Web";
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
            Periodo nuevoPeriodo = new Periodo(
                    (String) params.get("nombre"),
                    LocalDate.parse((String) params.get("fechaInicio")),
                    LocalDate.parse((String) params.get("fechaFin"))
            );

            // Llamada a la api
            ResponseEntity<Periodo> response = apiTemplateService.llamadaApi(
                    "/periodos/guardar/" + params.get("id"),
                    "PUT",
                    "Void",
                    nuevoPeriodo
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Periodo editado exitosamente");
            } else {
                System.out.println("Error al editar el periodo: " + response.getStatusCode());
            }

            String url = (String) params.getOrDefault("url", "paneldecontrol");
            String titulo = (String) params.getOrDefault("tit", "Título");

            model.addAttribute("url", url);
            model.addAttribute("titulo", titulo);
            model.addAttribute("org", sessionService.getOrg(session));
            return "fragments/Cabecera :: content";
        }
        return "Web";
    }

    @PostMapping(path = "buscar")
    public String buscarPeriodos(HttpSession session,
                                 @RequestParam Map<String, Object> params,
                                 Model model,
                                 HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String query = (String) params.getOrDefault("query", "");

            String fragment = "";
            if (query.isEmpty()) {
                fragment = cuerpoController.Periodos(session, params, model, request);
            } else {
                // Llamada a la api
                ResponseEntity<List<Periodo>> response = apiTemplateService.llamadaApi(
                        "/periodos/buscar/" + query,
                        "GET",
                        "Periodos",
                        null
                );

                model.addAttribute("periodos", response.getBody());

                fragment = "fragments/cuerpo/coordinacion/periodos/Periodos :: content";
            }

            return fragment;
        }
        return "Web";
    }
}
