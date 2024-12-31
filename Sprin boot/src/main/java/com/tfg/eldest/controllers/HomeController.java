package com.tfg.eldest.controllers;

import com.tfg.eldest.periodo.Periodo;
import com.tfg.eldest.usuario.Usuario;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public String home(HttpSession session,
                       @RequestParam Map<String, Object> params,
                       Model model) {
        sessionService.removeUserSession(session);
        return "Web";
    }

    @GetMapping(path = "app")
    public String app(HttpSession session,
                      @RequestParam Map<String, Object> params,
                      Model model) {
        return "Base";
    }

    private String obtenerPeriodoActual() {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/periodos/habilitados";

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

        LocalDate fechaActual = LocalDate.now();

        // Variable para almacenar el periodo más cercano
        Periodo periodoMasCercano = null;

        for (Periodo periodo : periodos) {
            // Si la fecha actual está dentro del rango de un periodo
            if (!fechaActual.isBefore(periodo.getInicio()) && !fechaActual.isAfter(periodo.getFin())) {
                return periodo.getId().toString(); // Retornar el ID del periodo actual
            }
            // Si la fecha actual está antes del primer periodo, o más tarde que el último, buscar el más cercano
            System.out.println(periodo.getInicio().isAfter(fechaActual));
            if (periodoMasCercano == null || fechaActual.isAfter(periodo.getFin())) {
                periodoMasCercano = periodo;
            }
        }
        System.out.println(periodoMasCercano.toString());
        // Si no se encuentra un periodo exacto, devolver el más cercano
        if (periodoMasCercano != null) {
            return periodoMasCercano.getId().toString();
        }

        return "1"; // Devuelve un periodo por defecto
    }

    @PostMapping(path = "login")
    public String login(HttpSession session,
                        @RequestParam Map<String, Object> params,
                        Model model) {
        String id = (String) params.get("id");
        String password = (String) params.get("password");

        if (id != null || password != null) {
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/usuarios/" + id;

            // Realizar la solicitud GET a la API de actividades
            ResponseEntity<Usuario> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Usuario>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            Usuario usuario = response.getBody();

            if (usuario.getPassword().equals(password)) {
                String coordinador = usuario.getRoles().stream()
                        .anyMatch(rol -> "Coordinador".equals(rol.getNombre())) ? "true" : "false";
                sessionService.setSession(session, id, obtenerPeriodoActual(), coordinador);
                return "pantallas/App :: content";
            }

        }
        return "pantallas/autenticacion/Login :: content";
    }

    @GetMapping(path = "index")
    public String index(HttpSession session,
                        @RequestParam Map<String, Object> params,
                        Model model) {
        boolean loggeado = sessionService.isUserLoggedIn(session);
        if (loggeado) {
            return "pantallas/App :: content";
        } else {
            return "pantallas/autenticacion/Login :: content";
        }
    }

    @GetMapping(path = "inicio")
    public String inicioGet(HttpSession session,
                            @RequestParam Map<String, Object> params,
                            Model model) {
        String nombreUsuario = sessionService.getUsuarioID(session);
        Boolean coordinacion = Boolean.parseBoolean(sessionService.getCoordinacion(session));
        String nombrePeriodo = sessionService.getNombrePeriodo(session);
        String primario = sessionService.getPrimario(session);
        String secundario = sessionService.getSecundario(session);
        String logo = sessionService.getLogo(session);

        model.addAttribute("usuario", nombreUsuario);
        model.addAttribute("periodo", nombrePeriodo);
        model.addAttribute("coordinacion", coordinacion);
        model.addAttribute("primario", primario);
        model.addAttribute("secundario", secundario);
        model.addAttribute("org", sessionService.getOrg(session));
        model.addAttribute("logo", logo);
        return "fragments/Body :: content";
    }

    @PostMapping(path = "inicio")
    public String inicioPost(HttpSession session,
                             @RequestParam Map<String, Object> params,
                             Model model) {
        String nombreUsuario = sessionService.getUsuarioID(session);
        Boolean coordinacion = Boolean.parseBoolean(sessionService.getCoordinacion(session));
        String periodoID = (String) params.getOrDefault("periodo", "Indefinido");
        String primario = sessionService.getPrimario(session);
        String secundario = sessionService.getSecundario(session);
        String logo = sessionService.getLogo(session);

        sessionService.setPeriodoID(session, periodoID);
        String nombrePeriodo = sessionService.getNombrePeriodo(session);

        model.addAttribute("usuario", nombreUsuario);
        model.addAttribute("periodo", nombrePeriodo);
        model.addAttribute("coordinacion", coordinacion);
        model.addAttribute("primario", primario);
        model.addAttribute("secundario", secundario);
        model.addAttribute("org", sessionService.getOrg(session));
        model.addAttribute("logo", logo);
        return "fragments/Body :: content";
    }

    @PostMapping(path = "cabecera")
    public String Cabecera(HttpSession session,
                           @RequestParam Map<String, Object> params,
                           Model model) {
        String url = (String) params.getOrDefault("url", "paneldecontrol");
        String titulo = (String) params.getOrDefault("titulo", "Título");
        String id = (String) params.getOrDefault("id", null);

        model.addAttribute("url", url);
        model.addAttribute("titulo", titulo);
        model.addAttribute("org", sessionService.getOrg(session));
        model.addAttribute("id", id);
        return "fragments/Cabecera :: content";
    }
}
