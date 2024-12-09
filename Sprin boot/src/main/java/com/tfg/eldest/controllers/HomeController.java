package com.tfg.eldest.controllers;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    @GetMapping(path = "/")
    public String indexGet(Model model) {
        model.addAttribute("usuario", "1111");
        model.addAttribute("periodo", "curso 20/21");
        model.addAttribute("coordinacion", false);
        return "Index";
    }

    @PostMapping(path = "/")
    public String index(@RequestParam Map<String, Object> params,
                        Model model) {
        String usuario = (String) params.getOrDefault("usuario", "Invitado");
        String periodo = (String) params.getOrDefault("periodo", "Indefinido");
        Boolean coordinacion = (Boolean) params.getOrDefault("coordinacion", false);

        model.addAttribute("usuario", usuario);
        model.addAttribute("periodo", periodo);
        model.addAttribute("coordinacion", coordinacion);
        return "Index";
    }

    @PostMapping(path = "/cabecera")
    public String Cabecera(@RequestParam Map<String, Object> params,
                           Model model) {
        String url = (String) params.getOrDefault("url", "paneldecontrol");
        String titulo = (String) params.getOrDefault("titulo", "Título");
        String periodo = (String) params.getOrDefault("periodo", "Indefinido");
        Boolean coordinacion = Boolean.valueOf((String) params.getOrDefault("coordinacion", false));

        model.addAttribute("url", url);
        model.addAttribute("titulo", titulo);
        model.addAttribute("org", "Asociación Juvenil Aguazella");
        model.addAttribute("periodo", periodo);
        model.addAttribute("coordinacion", coordinacion);
        return "fragments/Cabecera :: content"; // Renderiza un fragmento Thymeleaf
    }

    @PostMapping(path = "/cuerpo/paneldecontrol")
    public String PanelDeControl(@RequestParam Map<String, Object> params,
                                 Model model) {
        Boolean coordinacion = Boolean.valueOf((String) params.getOrDefault("coordinacion", false));

        model.addAttribute("coordinacion", coordinacion);
        return "fragments/cuerpo/PanelDeControl :: content"; // Renderiza un fragmento Thymeleaf
    }

    @PostMapping(path = "/cuerpo/actividades")
    public String Actividades(@RequestParam Map<String, Object> params,
                              Model model) {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/actividades";

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
        return "fragments/cuerpo/Actividades :: content"; // Renderiza un fragmento Thymeleaf
    }
}
