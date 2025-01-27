package com.tfg.eldest.frontend.services;

import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
import com.tfg.eldest.backend.periodo.Periodo;
import com.tfg.eldest.backend.permiso.Permiso;
import com.tfg.eldest.backend.personalizacion.Personalizacion;
import com.tfg.eldest.backend.rol.Rol;
import com.tfg.eldest.backend.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiTemplateService {
    @Value("${api.url}")
    private String apiUrl;

    // -- Servicios --
    @Autowired
    private SessionService sessionService;
    // ---------------

    public <T> ResponseEntity<T> llamadaApi(String endpoint, String metodo, String tipo, T request) {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        // Construir la URL completa de la API
        String url = this.apiUrl + endpoint;

        // Procesar el método
        HttpMethod method = switch (metodo) {
            case "GET" -> HttpMethod.GET;
            case "POST" -> HttpMethod.POST;
            case "PUT" -> HttpMethod.PUT;
            case "DELETE" -> HttpMethod.DELETE;
            default -> throw new IllegalStateException("Unexpected value for metodo: " + metodo);
        };

        // Crear requestEntity solo para métodos que lo requieran (todos excepto GET)
        HttpEntity<T> requestEntity = (method == HttpMethod.GET || request == null) ? null : new HttpEntity<>(request);

        // Obtener el ParameterizedTypeReference correspondiente al tipo usando un switch
        ParameterizedTypeReference responseType = switch (tipo) {
            case "ActividadFormacion" -> new ParameterizedTypeReference<ActividadFormacion>() {};
            case "ActividadesFormaciones" -> new ParameterizedTypeReference<List<ActividadFormacion>>() {};

            case "Periodo" -> new ParameterizedTypeReference<Periodo>() {};
            case "Periodos" -> new ParameterizedTypeReference<List<Periodo>>() {};

            case "Permiso" -> new ParameterizedTypeReference<Permiso>() {};
            case "Permisos" -> new ParameterizedTypeReference<List<Permiso>>() {};

            case "Personalizacion" -> new ParameterizedTypeReference<Personalizacion>() {};

            case "Rol" -> new ParameterizedTypeReference<Rol>() {};
            case "Roles" -> new ParameterizedTypeReference<List<Rol>>() {};

            case "Usuario" -> new ParameterizedTypeReference<Usuario>() {};
            case "Usuarios" -> new ParameterizedTypeReference<List<Usuario>>() {};

            case "Void" -> new ParameterizedTypeReference<Void>() {};

            default -> throw new IllegalStateException("Unexpected value for tipo: " + tipo);
        };

        try {
            return restTemplate.exchange(
                    url,
                    method,
                    requestEntity,
                    responseType
            );
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Manejo específico de errores HTTP (4xx, 5xx)
            throw new RuntimeException("Error al hacer la solicitud: " + e.getMessage(), e);
        } catch (Exception e) {
            // Manejo de cualquier otra excepción
            throw new RuntimeException("Error inesperado: " + e.getMessage(), e);
        }
    }
}

