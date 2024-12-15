package com.tfg.eldest.controllers;

import com.tfg.eldest.periodo.Periodo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SessionService {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    // Método para guardar datos en la sesión
    public void setSession(HttpSession session, String usuarioID, String periodoID) {
        session.setAttribute("usuarioID", usuarioID);
        session.setAttribute("periodoID", periodoID);
    }

    // Método para obtener el usuario de la sesión
    public String getUsuarioID(HttpSession session) {
        return (String) session.getAttribute("usuarioID");
    }

    // Método para obtener el período de la sesión
    public String getPeriodoID(HttpSession session) {
        return (String) session.getAttribute("periodoID");
    }

    // Método para guardar dato del período de la sesión
    public void setPeriodoID(HttpSession session, String periodoID) {
        session.setAttribute("periodoID", periodoID);
    }

    // Método para eliminar los datos de la sesión
    public void removeUserSession(HttpSession session) {
        session.removeAttribute("usuarioID");
        session.removeAttribute("periodoID");
    }

    // Método para verificar si un usuario está en sesión
    public boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute("usuarioID") != null; // Devuelve true si hay un usuario en sesión
    }

    public String getNombrePeriodo (HttpSession session) {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/periodos/" + session.getAttribute("periodoID");

        // Realizar la solicitud GET a la API
        ResponseEntity<Periodo> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Periodo>() {
                }
        );

        // Obtener la lista de actividades de la respuesta
        Periodo periodo = response.getBody();

        return periodo.getNombre();
    }
}
