package com.tfg.eldest.frontend.services;

import com.tfg.eldest.backend.periodo.Periodo;
import com.tfg.eldest.backend.usuario.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SessionService {
    @Value("${api.url}")
    private String apiUrl;

    // Método para guardar datos en la sesión
    public void setSession(HttpSession session, String usuarioID, String periodoID) {
        session.setAttribute("usuarioID", usuarioID);
        session.setAttribute("periodoID", periodoID);
    }

    // Método para obtener el id y nombre del usuario de la sesión
    public String getNombreUsuarioID(HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();

        // URL de la API que devuelve las actividades
        String apiUrl = this.apiUrl + "/usuarios/" + session.getAttribute("usuarioID");

        // Realizar la solicitud GET a la API
        ResponseEntity<Usuario> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Usuario>() {
                }
        );

        // Obtener la lista de actividades de la respuesta
        Usuario usuario = response.getBody();

        return (usuario.getId() + " - " + usuario.getNombre());
    }

    // Método para obtener el usuario de la sesión
    public String getUsuarioID(HttpSession session) {
        return (String) session.getAttribute("usuarioID");
    }

    // Método para obtener el nombre del período de la sesión
    public String getNombrePeriodo(HttpSession session) {
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

    // Método para obtener el período de la sesión
    public String getPeriodoID(HttpSession session) {
        String periodoID = (String) session.getAttribute("periodoID");
        return periodoID != null ? periodoID : "Indefinido";
    }

    // Método para guardar dato del período de la sesión
    public void setPeriodoID(HttpSession session, String periodoID) {
        session.setAttribute("periodoID", periodoID);
    }

    // Método para eliminar los datos de la sesión
    public void removeUserSession(HttpSession session) {
        session.removeAttribute("usuarioID");
        session.removeAttribute("periodoID");
        session.removeAttribute("org");
        session.removeAttribute("primario");
        session.removeAttribute("secundario");
        session.removeAttribute("logo");
    }

    // Método para verificar si un usuario está en sesión
    public boolean isUserLoggedIn(HttpSession session) {
        Object usuarioID = session.getAttribute("usuarioID");

        if (usuarioID != null) {
            // Crear un objeto RestTemplate para hacer la llamada a la API
            RestTemplate restTemplate = new RestTemplate();

            // URL de la API que devuelve las actividades
            String apiUrl = this.apiUrl + "/usuarios/" + usuarioID + "/existe";

            // Realizar la solicitud GET a la API
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Boolean>() {
                    }
            );

            // Obtener la lista de actividades de la respuesta
            return response.getBody(); // Devuelve true si hay un usuario valido en sesión
        }
        return false;
    }
}
