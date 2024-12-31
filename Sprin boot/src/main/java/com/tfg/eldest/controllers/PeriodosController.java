package com.tfg.eldest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tfg.eldest.periodo.Periodo;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/coordinacion/periodos")
public class PeriodosController {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CuerpoController cuerpoController;

    @PostMapping(path = "crear")
    public String Crear(HttpSession session,
                        @RequestParam Map<String, Object> params,
                        Model model) throws JsonProcessingException {

        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/periodos/crear";

        // Crear el objeto ActividadFormacion que se enviará en el cuerpo de la solicitud
        Periodo nuevoPeriodo = new Periodo(
                (String) params.get("nombre"),
                LocalDate.parse((String) params.get("fechaInicio")),
                LocalDate.parse((String) params.get("fechaFin"))
        );

        // Crear la entidad Http que contiene el cuerpo de la solicitud
        HttpEntity<Periodo> requestEntity = new HttpEntity<>(nuevoPeriodo);

        // Realizar la solicitud POST a la API de actividades
        ResponseEntity<Void> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                Void.class
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
        model.addAttribute("org", "Asociación Juvenil Aguazella");
        return "fragments/Cabecera :: content";
    }

    @PostMapping(path = "guardar")
    public String Guardar(HttpSession session,
                          @RequestParam Map<String, Object> params,
                          Model model) throws JsonProcessingException {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/periodos/guardar/" + params.get("id");

        // Crear el objeto ActividadFormacion que se enviará en el cuerpo de la solicitud
        Periodo nuevaVoluntario = new Periodo(
                (String) params.get("nombre"),
                LocalDate.parse((String) params.get("fechaInicio")),
                LocalDate.parse((String) params.get("fechaFin"))
        );

        // Crear la entidad Http que contiene el cuerpo de la solicitud
        HttpEntity<Periodo> requestEntity = new HttpEntity<>(nuevaVoluntario);

        // Realizar la solicitud GET a la API de actividades
        ResponseEntity<Void> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.PUT,
                requestEntity,
                Void.class
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
        model.addAttribute("org", "Asociación Juvenil Aguazella");
        return "fragments/Cabecera :: content";
    }

    @PostMapping(path = "buscar")
    public String buscarPeriodos(HttpSession session,
                                    @RequestParam Map<String, Object> params,
                                    Model model) {
        String query = (String) params.getOrDefault("query", "");

        String fragment = "";
        if (query.isEmpty()) {
            fragment = cuerpoController.Periodos(session, params, model);
        } else {
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/periodos/buscar/" + query;

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

            fragment = "fragments/cuerpo/coordinacion/periodos/Periodos :: content";
        }

        return fragment;
    }
}
