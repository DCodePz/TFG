package com.tfg.eldest.frontend.controllers;

import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "coordinacion/personalizacion")
public class PersonalizacionesController {
    // -- Servicios --
    @Autowired
    private SessionService sessionService;
    @Autowired
    private PermisosService permisosService;
    @Autowired
    private ApiTemplateService apiTemplateService;
    // ---------------

    @Autowired
    private HomeController homeController;

    @PostMapping(path = "cambiar")
    public String CambiarDatos(HttpSession session,
                               @RequestParam Map<String, Object> params,
                               Model model,
                               HttpServletRequest request) {
        String usuarioId = sessionService.getUsuarioID(session);
        String path = request.getRequestURI();
        if (permisosService.comprobarPermisos(usuarioId, path)) {
            // Crear el objeto Personalizacion que se enviará en el cuerpo de la solicitud
            Personalizacion nuevaPersonalizacion = new Personalizacion(
                    (String) params.get("org"),
                    (String) params.get("primario")
            );

            // Llamada a la api
            ResponseEntity<Personalizacion> response = apiTemplateService.llamadaApi(
                    "/personalizacion/guardar",
                    "PUT",
                    "Void",
                    nuevaPersonalizacion
            );

            return "pantallas/App";
        }
        return homeController.home(session,params,model,request);
    }
}
