package com.tfg.eldest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tfg.eldest.actividadformacion.ActividadFormacion;
import com.tfg.eldest.rol.Rol;
import com.tfg.eldest.usuario.Usuario;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/coordinacion/voluntarios")
public class VoluntariosController {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CuerpoController cuerpoController;

    private List<Rol> obtenerRoles(@RequestParam Map<String, Object> params) {
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
                        Model model) throws JsonProcessingException {

        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/usuarios/crear";

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

        // Crear la entidad Http que contiene el cuerpo de la solicitud
        HttpEntity<Usuario> requestEntity = new HttpEntity<>(nuevoVoluntario);

        // Realizar la solicitud POST a la API de actividades
        ResponseEntity<Void> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                Void.class
        );

        // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Voluntario creado exitosamente");
        } else {
            System.out.println("Error al crear el voluntario: " + response.getStatusCode());
        }

        String url = (String) params.getOrDefault("url", "paneldecontrol");
        String titulo = (String) params.getOrDefault("tit", "Título");

        model.addAttribute("url", url);
        model.addAttribute("titulo", titulo);
        model.addAttribute("org", sessionService.getOrg(session));
        return "fragments/Cabecera :: content";
    }

    @PostMapping(path = "guardar")
    public String Guardar(HttpSession session,
                          @RequestParam Map<String, Object> params,
                          Model model) throws JsonProcessingException {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/usuarios/guardar/" + params.get("id");

        // Crear el objeto ActividadFormacion que se enviará en el cuerpo de la solicitud
        Usuario nuevaVoluntario = new Usuario(
                Long.parseLong((String) params.get("id")),
                (String) params.get("nombre"),
                (String) params.get("apellidos"),
                (String) params.get("email"),
                (String) params.get("password"),
                "Voluntario",
                obtenerRoles(params)
        );

        // Crear la entidad Http que contiene el cuerpo de la solicitud
        HttpEntity<Usuario> requestEntity = new HttpEntity<>(nuevaVoluntario);

        // Realizar la solicitud GET a la API de actividades
        ResponseEntity<Void> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.PUT,
                requestEntity,
                Void.class
        );

        // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Voluntario editado exitosamente");
        } else {
            System.out.println("Error al editar el voluntarioi: " + response.getStatusCode());
        }

        String url = (String) params.getOrDefault("url", "paneldecontrol");
        String titulo = (String) params.getOrDefault("tit", "Título");

        model.addAttribute("url", url);
        model.addAttribute("titulo", titulo);
        model.addAttribute("org", sessionService.getOrg(session));
        return "fragments/Cabecera :: content";
    }

    @PostMapping(path = "buscar")
    public String buscarVoluntarios(HttpSession session,
                                    @RequestParam Map<String, Object> params,
                                    Model model) {
        String query = (String) params.getOrDefault("query", "");

        String fragment = "";
        if (query.isEmpty()) {
            fragment = cuerpoController.Voluntarios(session, params, model);
        } else {
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/usuarios/voluntarios/buscar/" + query;

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

            fragment = "fragments/cuerpo/coordinacion/voluntarios/Voluntarios :: content";
        }

        return fragment;
    }
}
