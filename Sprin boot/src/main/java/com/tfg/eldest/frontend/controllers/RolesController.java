package com.tfg.eldest.frontend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tfg.eldest.backend.permiso.Permiso;
import com.tfg.eldest.backend.rol.Rol;
import com.tfg.eldest.frontend.services.PermisosService;
import com.tfg.eldest.frontend.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/coordinacion/roles")
public class RolesController {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CuerpoController cuerpoController;

    @Autowired
    private PermisosService permisosService;

    private List<Permiso> obtenerPermisos(@RequestParam Map<String, Object> params) {
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

        List<Permiso> seleccionados = new ArrayList<>();
        for (Permiso permiso : permisos) {
            String id = (String) params.get("permiso " + permiso.getId());
            if (id != null && !id.isEmpty()) {
                seleccionados.add(permiso);
            }
        }
        System.out.println(seleccionados);
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
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/roles/crear";

            // Crear el objeto ActividadFormacion que se enviará en el cuerpo de la solicitud
            Rol nuevoRol = new Rol(
                    (String) params.get("nombre"),
                    obtenerPermisos(params)
            );

            // Crear la entidad Http que contiene el cuerpo de la solicitud
            HttpEntity<Rol> requestEntity = new HttpEntity<>(nuevoRol);

            // Realizar la solicitud POST a la API de actividades
            ResponseEntity<Void> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Rol creado exitosamente");
            } else {
                System.out.println("Error al crear el rol: " + response.getStatusCode());
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
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/roles/guardar/" + params.get("id");

            // Crear el objeto ActividadFormacion que se enviará en el cuerpo de la solicitud
            Rol nuevaVoluntario = new Rol(
                    (String) params.get("nombre"),
                    obtenerPermisos(params)
            );

            // Crear la entidad Http que contiene el cuerpo de la solicitud
            HttpEntity<Rol> requestEntity = new HttpEntity<>(nuevaVoluntario);

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<Void> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.PUT,
                    requestEntity,
                    Void.class
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Rol editado exitosamente");
            } else {
                System.out.println("Error al editar el rol: " + response.getStatusCode());
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
}
