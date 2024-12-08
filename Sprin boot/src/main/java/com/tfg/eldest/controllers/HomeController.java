package com.tfg.eldest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping(path = "/")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Inicio");
        return "index";
    }

    @GetMapping(path = "/dashboard")
    public String home(@RequestParam(name = "url", defaultValue = "content") String url,
            @RequestParam(name = "titulo", defaultValue = "Título") String titulo, Model model) {
        model.addAttribute("titulo", titulo);
        return "fragments/dashboard :: " + url; // Renderiza un fragmento Thymeleaf
    }

    @GetMapping(path = "/main/home")
    public String home2(@RequestParam(name = "url", defaultValue = "content") String url, Model model) {
        return "fragments/home2 :: " + url; // Renderiza un fragmento Thymeleaf
    }
}
