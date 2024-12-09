package com.tfg.eldest.controllers;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import com.tfg.eldest.periodo.Periodo;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.time.Month.JUNE;
import static java.time.Month.SEPTEMBER;

@Controller
@RequestMapping("/modals")
public class ModalsController {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    @PostMapping(path = "/periodo/cambiar")
    public String cambiarPeriodo(@RequestParam Map<String, Object> params,
                                 Model model) {
//        // Crear un objeto RestTemplate para hacer la llamada a la API
//        RestTemplate restTemplate = new RestTemplate();
//
//        // URL de la API que devuelve los periodos
//        String apiUrl = this.apiUrl + "/periodos";
//
//        // Realizar la solicitud GET a la API de periodos
//        ResponseEntity<List<Periodo>> response = restTemplate.exchange(
//                apiUrl,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Periodo>>() {
//                }
//        );
//
//        // Obtener la lista de periodos de la respuesta
//        List<Periodo> periodos = response.getBody();
//        System.out.println(periodos);
        List<Periodo> periodos = List.of(new Periodo(4L, "curso 22/23", LocalDate.of(2022, SEPTEMBER, 4),
                LocalDate.of(2023, JUNE, 24)));
        model.addAttribute("periodos", periodos);

        Boolean coordinacion = Boolean.valueOf((String) params.getOrDefault("coordinacion", false));
        model.addAttribute("coordinacion", coordinacion);
        return "fragments/modals/ModalesPeriodo :: modalCambiarPeriodo"; // Renderiza un fragmento Thymeleaf
    }
}
