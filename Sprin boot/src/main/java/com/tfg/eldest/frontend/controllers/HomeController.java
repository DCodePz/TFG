package com.tfg.eldest.frontend.controllers;

import com.tfg.eldest.backend.periodo.Periodo;
import com.tfg.eldest.backend.personalizacion.Personalizacion;
import com.tfg.eldest.backend.usuario.Usuario;
import com.tfg.eldest.frontend.services.ApiTemplateService;
import com.tfg.eldest.frontend.services.PermisosService;
import com.tfg.eldest.frontend.services.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {
    // -- Servicios --
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermisosService permisosService;
    @Autowired
    private ApiTemplateService apiTemplateService;
    // ---------------

    @GetMapping
    public String home(HttpSession session,
                       @RequestParam Map<String, Object> params,
                       Model model,
                       HttpServletRequest request) {
        sessionService.removeUserSession(session);
        // Llamada a la api
        ResponseEntity<Personalizacion> response = apiTemplateService.llamadaApi(
                "/personalizacion",
                "GET",
                "Personalizacion",
                null
        );
        Personalizacion personalizacion = response.getBody();

        model.addAttribute("foto", personalizacion.getFoto());
        model.addAttribute("primario", personalizacion.getColor());
        model.addAttribute("nombre", personalizacion.getNombre());
        System.out.println(personalizacion.getFoto());
        return "Web";
    }

    @GetMapping(path = "app")
    public String app(HttpSession session,
                      @RequestParam Map<String, Object> params,
                      Model model,
                      HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            return "Base";
        }
        return home(session,params,model,request);
    }

    private String obtenerPeriodoActual() {
        // Llamada a la api
        ResponseEntity<List<Periodo>> response = apiTemplateService.llamadaApi(
                "/periodos/habilitados",
                "GET",
                "Periodos",
                null
        );

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

        // Si no se encuentra un periodo exacto, devolver el más cercano
        if (periodoMasCercano != null) {
            return periodoMasCercano.getId().toString();
        }

        return "1"; // Devuelve un periodo por defecto
    }

    @PostMapping(path = "login")
    public String login(HttpSession session,
                        @RequestParam Map<String, Object> params,
                        Model model,
                        HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String id = (String) params.get("id");
            String password = (String) params.get("password");

            if (id != null || password != null) {
                // Llamada a la api
                ResponseEntity<Usuario> response = apiTemplateService.llamadaApi(
                        "/usuarios/" + id,
                        "GET",
                        "Usuario",
                        null
                );

                Usuario usuario = response.getBody();

                if (usuario.getPassword().equals(password) && usuario.getHabilitado()) {
                    sessionService.setSession(session, id, obtenerPeriodoActual());
                    return "pantallas/App :: content";
                }

            }

            // Llamada a la api
            ResponseEntity<Personalizacion> response = apiTemplateService.llamadaApi(
                    "/personalizacion",
                    "GET",
                    "Personalizacion",
                    null
            );
            Personalizacion personalizacion = response.getBody();
            model.addAttribute("primario", personalizacion.getColor());
            return "pantallas/autenticacion/Login :: content";
        }
        return home(session,params,model,request);
    }

    @GetMapping(path = "index")
    public String index(HttpSession session,
                        @RequestParam Map<String, Object> params,
                        Model model,
                        HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            boolean loggeado = sessionService.isUserLoggedIn(session);
            if (loggeado) {
                return "pantallas/App :: content";
            } else {
                // Llamada a la api
                ResponseEntity<Personalizacion> response = apiTemplateService.llamadaApi(
                        "/personalizacion",
                        "GET",
                        "Personalizacion",
                        null
                );
                Personalizacion personalizacion = response.getBody();
                model.addAttribute("primario", personalizacion.getColor());
                return "pantallas/autenticacion/Login :: content";
            }
        }
        return home(session,params,model,request);
    }

    @GetMapping(path = "inicio")
    public String inicioGet(HttpSession session,
                            @RequestParam Map<String, Object> params,
                            Model model,
                            HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Llamada a la api
            ResponseEntity<Personalizacion> response2 = apiTemplateService.llamadaApi(
                    "/personalizacion",
                    "GET",
                    "Personalizacion",
                    null
            );
            Personalizacion personalizacion = response2.getBody();

            String nombreUsuario = sessionService.getNombreUsuarioID(session);
            Boolean coordinacion = permisosService.comprobarPermisos(sessionService.getUsuarioID(session), "/cuerpo/coordinacion");
            String nombrePeriodo = sessionService.getNombrePeriodo(session);
            String primario = personalizacion.getColor();
            String org = personalizacion.getNombre();
            String logo = personalizacion.getFoto();

            model.addAttribute("usuario", nombreUsuario);
            model.addAttribute("periodo", nombrePeriodo);
            model.addAttribute("coordinacion", coordinacion);
            model.addAttribute("primario", primario);
            model.addAttribute("org", org);
            model.addAttribute("logo", logo);
            return "fragments/Body :: content";
        }
        return home(session,params,model,request);
    }

    @PostMapping(path = "inicio")
    public String inicioPost(HttpSession session,
                             @RequestParam Map<String, Object> params,
                             Model model,
                             HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Llamada a la api
            ResponseEntity<Personalizacion> response2 = apiTemplateService.llamadaApi(
                    "/personalizacion",
                    "GET",
                    "Personalizacion",
                    null
            );
            Personalizacion personalizacion = response2.getBody();

            String nombreUsuario = sessionService.getNombreUsuarioID(session);
            Boolean coordinacion = permisosService.comprobarPermisos(sessionService.getUsuarioID(session), "/cuerpo/coordinacion");
            String periodoID = (String) params.getOrDefault("periodo", "Indefinido");
            String primario = personalizacion.getColor();
            String org = personalizacion.getNombre();
            String logo = personalizacion.getFoto();

            sessionService.setPeriodoID(session, periodoID);
            String nombrePeriodo = sessionService.getNombrePeriodo(session);

            model.addAttribute("usuario", nombreUsuario);
            model.addAttribute("periodo", nombrePeriodo);
            model.addAttribute("coordinacion", coordinacion);
            model.addAttribute("primario", primario);
            model.addAttribute("org", org);
            model.addAttribute("logo", logo);
            return "fragments/Body :: content";
        }
        return home(session,params,model,request);
    }

    @PostMapping(path = "cabecera")
    public String Cabecera(HttpSession session,
                           @RequestParam Map<String, Object> params,
                           Model model,
                           HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Llamada a la api
            ResponseEntity<Personalizacion> response2 = apiTemplateService.llamadaApi(
                    "/personalizacion",
                    "GET",
                    "Personalizacion",
                    null
            );
            Personalizacion personalizacion = response2.getBody();

            String url = (String) params.getOrDefault("url", "paneldecontrol");
            String titulo = (String) params.getOrDefault("titulo", "Título");
            String org = personalizacion.getNombre();
            String id = (String) params.getOrDefault("id", null);

            model.addAttribute("url", url);
            model.addAttribute("titulo", titulo);
            model.addAttribute("org", org);
            model.addAttribute("id", id);
            return "fragments/Cabecera :: content";
        }
        return home(session,params,model,request);
    }
}
