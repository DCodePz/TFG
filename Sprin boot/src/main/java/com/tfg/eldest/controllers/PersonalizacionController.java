package com.tfg.eldest.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping(path = "coordinacion/personalizacion")
public class PersonalizacionController {
    @Autowired
    private SessionService sessionService;

    @Autowired
    private HomeController homeController;

    @PostMapping(path = "cambiar")
    public String CambiarDatos(HttpSession session,
                              @RequestParam Map<String, Object> params,
                              Model model) {

        String org = (String) params.get("org");
        String primario = (String) params.get("primario");
        String secundario = (String) params.get("secundario");
        String logo = (String) params.getOrDefault("logo", "default");

        org = org.isEmpty() ? "Organizacion" : org;
        primario = primario.isEmpty() ? "#212529" : primario;
        secundario = secundario.isEmpty() ? "#212529" : secundario;
        logo = logo.isEmpty() ? "default" : logo;

        sessionService.setPersonalizacion(session, org, primario, secundario, logo);

        return "pantallas/App";
    }
}
