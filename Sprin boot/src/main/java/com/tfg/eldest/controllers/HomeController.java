package com.tfg.eldest.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private SessionService sessionService;

    @GetMapping
    public String index(HttpSession session, Model model) {
        sessionService.setSession(session, "1", "1");
        return "Base";
    }

    @GetMapping(path = "inicio")
    public String inicioGet(HttpSession session,
                            @RequestParam Map<String, Object> params,
                            Model model) {
        String nombreUsuario = sessionService.getUsuarioID(session);
        Boolean coordinacion = true; // TODO: Quitar el por defecto
        String nombrePeriodo = sessionService.getNombrePeriodo(session);

        model.addAttribute("usuario", nombreUsuario);
        model.addAttribute("periodo", nombrePeriodo);
        model.addAttribute("coordinacion", coordinacion);
        return "fragments/Body :: content";
    }

    @PostMapping(path = "inicio")
    public String inicioPost(HttpSession session,
                             @RequestParam Map<String, Object> params,
                             Model model) {
        String nombreUsuario = sessionService.getUsuarioID(session);
        Boolean coordinacion = true; // TODO: Quitar el por defecto
        String periodoID = (String) params.getOrDefault("periodo", "Indefinido");

        sessionService.setPeriodoID(session, periodoID);
        String nombrePeriodo = sessionService.getNombrePeriodo(session);

        model.addAttribute("usuario", nombreUsuario);
        model.addAttribute("periodo", nombrePeriodo);
        model.addAttribute("coordinacion", coordinacion);
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
        model.addAttribute("org", "Asociación Juvenil Aguazella");
        model.addAttribute("id", id);
        return "fragments/Cabecera :: content";
    }
}
