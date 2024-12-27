package com.tfg.eldest.controllers;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/formaciones")
public class FormacionesController {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CuerpoController cuerpoController;

    private List<Usuario> obtenerEncargados(@RequestParam Map<String, Object> params) {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/usuarios";

        // Realizar la solicitud GET a la API de actividades
        ResponseEntity<List<Usuario>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Usuario>>() {
                }
        );

        // Obtener la lista de actividades de la respuesta
        List<Usuario> usuarios = response.getBody();

        List<Usuario> encargados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            String id = (String) params.get("encargado " + usuario.getId());
            if (id != null && !id.isEmpty()) {
                encargados.add(usuario);
            }
        }
        return encargados;
    }

    private Periodo obtenerPeriodoActual(String periodoID) {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/periodos/" + periodoID;

        // Realizar la solicitud GET a la API de actividades
        ResponseEntity<Periodo> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Periodo>() {
                }
        );

        // Obtener la lista de actividades de la respuesta
        Periodo periodo = response.getBody();
        return periodo;
    }

    @PostMapping(path = "crear")
    public String Crear(HttpSession session,
                        @RequestParam Map<String, Object> params,
                        Model model) {
        String periodoID = sessionService.getPeriodoID(session);

        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las formaciones
        String apiUrl = this.apiUrl + "/formaciones/crear/" + periodoID;

        String grupo_edad = "";
        if (params.get("pequeños_pequeños") != null && !params.get("pequeños_pequeños").equals("")) {
            grupo_edad += params.get("pequeños_pequeños") + ", ";
        }
        if (params.get("pequeños_grandes") != null && !params.get("pequeños_grandes").equals("")) {
            grupo_edad += params.get("pequeños_grandes") + ", ";
        }
        if (params.get("medianos") != null && !params.get("medianos").equals("")) {
            grupo_edad += params.get("medianos") + ", ";
        }
        if (params.get("mayores") != null && !params.get("mayores").equals("")) {
            grupo_edad += params.get("mayores");
        }

        // Crear el objeto ActividadFormacion que se enviará en el cuerpo de la solicitud
        ActividadFormacion nuevaActividad = new ActividadFormacion(
                (String) params.get("numero"),
                (String) params.get("titulo"),
                LocalDate.parse((String) params.get("fech_realiz")),
                obtenerEncargados(params),
                Integer.parseInt((String) params.get("numVoluntarios")),
                Integer.parseInt((String) params.get("numParticipantes")),
                grupo_edad,
                Integer.parseInt((String) params.get("duracion")),
                (String) params.get("objetivos"),
                (String) params.get("materiales"),
                (String) params.get("descripcion"),
                (String) params.get("observaciones"),
                obtenerPeriodoActual(sessionService.getPeriodoID(session))
        );

        // Crear la entidad Http que contiene el cuerpo de la solicitud
        HttpEntity<ActividadFormacion> requestEntity = new HttpEntity<>(nuevaActividad);

        // Realizar la solicitud GET a la API de actividades
        ResponseEntity<Void> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                Void.class
        );

        // Verificar la respuesta (por ejemplo, si el código de 9estado es 200 OK)
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Formación creada exitosamente");
        } else {
            System.out.println("Error al crear la formación: " + response.getStatusCode());
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
                          Model model) {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/formaciones/guardar/" + params.get("id");

        String grupo_edad = "";
        if (params.get("pequeños_pequeños") != null && !params.get("pequeños_pequeños").equals("")) {
            grupo_edad += params.get("pequeños_pequeños") + ", ";
        }
        if (params.get("pequeños_grandes") != null && !params.get("pequeños_grandes").equals("")) {
            grupo_edad += params.get("pequeños_grandes") + ", ";
        }
        if (params.get("medianos") != null && !params.get("medianos").equals("")) {
            grupo_edad += params.get("medianos") + ", ";
        }
        if (params.get("mayores") != null && !params.get("mayores").equals("")) {
            grupo_edad += params.get("mayores");
        }

        // Crear el objeto ActividadFormacion que se enviará en el cuerpo de la solicitud
        ActividadFormacion nuevaFormacion = new ActividadFormacion(
                (String) params.get("numero"),
                (String) params.get("titulo"),
                LocalDate.parse((String) params.get("fech_realiz")),
                obtenerEncargados(params),
                Integer.parseInt((String) params.get("numVoluntarios")),
                Integer.parseInt((String) params.get("numParticipantes")),
                grupo_edad,
                Integer.parseInt((String) params.get("duracion")),
                (String) params.get("objetivos"),
                (String) params.get("materiales"),
                (String) params.get("descripcion"),
                (String) params.get("observaciones"),
                obtenerPeriodoActual(sessionService.getPeriodoID(session))
        );

        // Crear la entidad Http que contiene el cuerpo de la solicitud
        HttpEntity<ActividadFormacion> requestEntity = new HttpEntity<>(nuevaFormacion);

        // Realizar la solicitud GET a la API de actividades
        ResponseEntity<Void> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.PUT,
                requestEntity,
                Void.class
        );

        // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Formación creada exitosamente");
        } else {
            System.out.println("Error al crear la formación: " + response.getStatusCode());
        }

        String url = (String) params.getOrDefault("url", "paneldecontrol");
        String titulo = (String) params.getOrDefault("tit", "Título");

        model.addAttribute("url", url);
        model.addAttribute("titulo", titulo);
        model.addAttribute("org", "Asociación Juvenil Aguazella");
        return "fragments/Cabecera :: content";
    }

    @PostMapping("buscar")
    public String buscarActividades(HttpSession session,
                                    @RequestParam Map<String, Object> params,
                                    Model model) {
        String query = (String) params.getOrDefault("query", "");
        String periodoID = sessionService.getPeriodoID(session);

        String fragment = "";
        if (query.isEmpty()) {
            fragment = cuerpoController.Formaciones(session, params, model);
        } else {
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/formaciones/buscar/" + query + "/" + periodoID;

            // Realizar la solicitud GET a la API de formaciones
            ResponseEntity<List<ActividadFormacion>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ActividadFormacion>>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            List<ActividadFormacion> formaciones = response.getBody();

            model.addAttribute("formaciones", formaciones);

            fragment = "fragments/cuerpo/formaciones/Formaciones :: content";
        }

        return fragment;
    }

}
