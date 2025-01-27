package com.tfg.eldest.frontend.controllers;

import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/formaciones")
public class FormacionesController {
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
    @Autowired
    private HomeController homeController;

    private List<Usuario> obtenerEncargados(@RequestParam Map<String, Object> params) {
        // Llamada a la api
        ResponseEntity<List<Usuario>> response = apiTemplateService.llamadaApi(
                "/usuarios",
                "GET",
                "Usuarios",
                null
        );

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
        // Llamada a la api
        ResponseEntity<Periodo> response = apiTemplateService.llamadaApi(
                "/periodos/" + periodoID,
                "GET",
                "Periodo",
                null
        );

        return response.getBody();
    }

    @PostMapping(path = "crear")
    public String Crear(HttpSession session,
                        @RequestParam Map<String, Object> params,
                        Model model,
                        HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String periodoID = sessionService.getPeriodoID(session);

            // Procesar grupo_edad
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

            // Llamada a la api
            ResponseEntity<ActividadFormacion> response = apiTemplateService.llamadaApi(
                    "/formaciones/crear/" + periodoID,
                    "POST",
                    "Void",
                    nuevaFormacion
            );

            // Verificar la respuesta (por ejemplo, si el código de 9estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Formación creada exitosamente");
            } else {
                System.out.println("Error al crear la formación: " + response.getStatusCode());
            }

            // Llamada a la api
            ResponseEntity<Personalizacion> response2 = apiTemplateService.llamadaApi(
                    "/personalizacion",
                    "GET",
                    "Personalizacion",
                    null
            );
            Personalizacion personalizacion = response2.getBody();

            String url = (String) params.getOrDefault("url", "paneldecontrol");
            String titulo = (String) params.getOrDefault("tit", "Título");

            model.addAttribute("url", url);
            model.addAttribute("titulo", titulo);
            model.addAttribute("org", personalizacion.getNombre());
            return "fragments/Cabecera :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping(path = "guardar")
    public String Guardar(HttpSession session,
                          @RequestParam Map<String, Object> params,
                          Model model,
                          HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Procesar grupo_edad
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

            // Llamada a la api
            ResponseEntity<ActividadFormacion> response = apiTemplateService.llamadaApi(
                    "/formaciones/guardar/" + params.get("id"),
                    "PUT",
                    "Void",
                    nuevaFormacion
            );

            // Verificar la respuesta (por ejemplo, si el código de estado es 200 OK)
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Formación creada exitosamente");
            } else {
                System.out.println("Error al crear la formación: " + response.getStatusCode());
            }

            // Llamada a la api
            ResponseEntity<Personalizacion> response2 = apiTemplateService.llamadaApi(
                    "/personalizacion",
                    "GET",
                    "Personalizacion",
                    null
            );
            Personalizacion personalizacion = response2.getBody();

            String url = (String) params.getOrDefault("url", "paneldecontrol");
            String titulo = (String) params.getOrDefault("tit", "Título");

            model.addAttribute("url", url);
            model.addAttribute("titulo", titulo);
            model.addAttribute("org", personalizacion.getNombre());
            return "fragments/Cabecera :: content";
        }
        return homeController.home(session,params,model,request);
    }

    @PostMapping("buscar")
    public String buscarActividades(HttpSession session,
                                    @RequestParam Map<String, Object> params,
                                    Model model,
                                    HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            String query = (String) params.getOrDefault("query", "");
            String periodoID = sessionService.getPeriodoID(session);

            String fragment = "";
            if (query.isEmpty()) {
                fragment = cuerpoController.Formaciones(session, params, model, request);
            } else {
                // Llamada a la api
                ResponseEntity<List<ActividadFormacion>> response = apiTemplateService.llamadaApi(
                        "/formaciones/buscar/" + query + "/" + periodoID,
                        "GET",
                        "ActividadesFormaciones",
                        null
                );

                model.addAttribute("formaciones", response.getBody());

                fragment = "fragments/cuerpo/formaciones/Formaciones :: content";
            }

            return fragment;
        }
        return homeController.home(session,params,model,request);
    }

}
