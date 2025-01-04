package com.tfg.eldest.frontend.pdfs;

import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
import com.tfg.eldest.backend.periodo.Periodo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "pdfs")
public class PdfController {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    private Periodo obtenerPeriodo(Long id) {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = this.apiUrl + "/periodo/" + id;

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

    private ActividadFormacion obtenerActividadFormacion(String tipo, Long id) {
        // Crear un objeto RestTemplate para hacer la llamada a la API
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = this.apiUrl;
        if (tipo.equals("Actividad")) {
            // URL de la API que devuelve las actividades
            apiUrl = this.apiUrl + "/actividades/" + id;

        } else {
            // URL de la API que devuelve las actividades
            apiUrl = this.apiUrl + "/formaciones/" + id;
        }

        // Realizar la solicitud GET a la API de actividades
        ResponseEntity<ActividadFormacion> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ActividadFormacion>() {
                }
        );

        // Obtener la lista de actividades de la respuesta
        ActividadFormacion actividadFormacion = response.getBody();

        return actividadFormacion;
    }

    @GetMapping("generar/{tipo}/{id}")
    public void generatePdf(@PathVariable String tipo, @PathVariable Long id,
                            HttpServletResponse response) throws IOException {
        // Obtener la actividad de formación
        ActividadFormacion actividad = obtenerActividadFormacion(tipo, id);
        System.out.println(actividad);

        // Formatear los encargados y participantes como una cadena HTML
        String encargadosHtml = actividad.getEncargados().stream()
                .map(encargado -> "<li>" + encargado.getId() + "</li>")
                .collect(Collectors.joining());

        String participantesHtml = actividad.getParticipantes().stream()
                .map(participante -> "<li>" + participante.getId() + "</li>")
                .collect(Collectors.joining());

        // Define el path a la plantilla HTML
        String templatePath = "src/main/resources/templates/pdfs/PdfActividadFormacion.html";

        // Mapear los valores dinámicos para la plantilla
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("titulo", actividad.getTitulo());
        placeholders.put("tipo", actividad.getTipo());
        placeholders.put("numero", actividad.getNumero());
        placeholders.put("fech_realiz", String.valueOf(actividad.getFech_realiz()));
        placeholders.put("encargados", encargadosHtml);  // Se pasa la lista formateada
        placeholders.put("participantes", participantesHtml);  // Se pasa la lista formateada
        placeholders.put("grupo_edad", actividad.getGrupo_edad());
        placeholders.put("duracion", String.valueOf(actividad.getDuracion()));
        placeholders.put("objetivos", actividad.getObjetivos());
        placeholders.put("materiales", actividad.getMateriales());
        placeholders.put("descripcion", actividad.getDescripcion());
        placeholders.put("observaciones", actividad.getObservaciones());
        placeholders.put("numVoluntarios", String.valueOf(actividad.getNumVoluntarios()));
        placeholders.put("numParticipantes", String.valueOf(actividad.getNumParticipantes()));
        placeholders.put("visible", String.valueOf(actividad.getVisible()));
        placeholders.put("periodo", actividad.getPeriodo().getNombre());

        // Genera el PDF como un array de bytes
        byte[] pdfContents = pdfService.generatePdf(templatePath, placeholders);

        // Configura los encabezados de la respuesta para que el PDF se muestre correctamente
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + tipo + "_" + id + ".pdf");

        // Escribe el contenido PDF en la salida
        response.getOutputStream().write(pdfContents);
        response.getOutputStream().flush();
    }


}
