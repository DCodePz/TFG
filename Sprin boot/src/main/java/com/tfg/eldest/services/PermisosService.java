package com.tfg.eldest.services;

import com.tfg.eldest.permiso.Permiso;
import com.tfg.eldest.rol.Rol;
import com.tfg.eldest.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PermisosService {
    @Value("${api.url}")  // Cargar la URL desde application.properties
    private String apiUrl;

    // URL -> Permisos
    private static final Map<String, Set<String>> accesosVoluntarios = new HashMap<>();
    static {
        // ActividadesController
        accesosVoluntarios.put("/actividades/crear",
                new HashSet<>(Collections.singletonList("C_ACT")));
        accesosVoluntarios.put("/actividades/guardar",
                new HashSet<>(Arrays.asList("E_ACT","E_M_ACT")));
        accesosVoluntarios.put("/actividades/buscar",
                new HashSet<>(Collections.singletonList("V_ACT")));

        // CuerpoController
        accesosVoluntarios.put("/cuerpo/paneldecontrol",
                new HashSet<>(Arrays.asList(
                        "C_ACT", "V_ACT", "E_M_ACT", "E_ACT", "B_M_ACT", "B_ACT", "I_M_ACT", "I_ACT", "Ev_M_ACT", "Ev_ACT",
                        "C_FOR", "V_FOR", "E_M_FOR", "E_FOR", "B_M_FOR", "B_FOR", "I_M_FOR", "I_FOR"
                ))); // P_CTRL
        accesosVoluntarios.put("/cuerpo/coordinacion",
                new HashSet<>(Arrays.asList(
                        "C_VOL", "V_VOL", "E_VOL", "IH_VOL",
                        "C_ROL", "V_ROL", "E_ROL",
                        "C_PER", "V_PER", "E_PER", "IH_PER",
                        "CUSTOM"
                ))); // P_COORD
        accesosVoluntarios.put("/cuerpo/coordinacion/voluntarios",
                new HashSet<>(Collections.singletonList("V_VOL")));
        accesosVoluntarios.put("/cuerpo/coordinacion/voluntarios/nuevo",
                new HashSet<>(Collections.singletonList("C_VOL")));
        accesosVoluntarios.put("/cuerpo/coordinacion/voluntarios/editar",
                new HashSet<>(Collections.singletonList("E_VOL")));
        accesosVoluntarios.put("/cuerpo/coordinacion/voluntarios/in_habilitar",
                new HashSet<>(Collections.singletonList("IH_VOL")));
        accesosVoluntarios.put("/cuerpo/coordinacion/roles",
                new HashSet<>(Collections.singletonList("V_ROL")));
        accesosVoluntarios.put("/cuerpo/coordinacion/roles/nuevo",
                new HashSet<>(Collections.singletonList("C_ROL")));
        accesosVoluntarios.put("/cuerpo/coordinacion/roles/editar",
                new HashSet<>(Collections.singletonList("E_ROL")));
        accesosVoluntarios.put("/cuerpo/coordinacion/roles/ver",
                new HashSet<>(Collections.singletonList("V_ROL")));
        accesosVoluntarios.put("/cuerpo/coordinacion/roles/in_habilitar",
                new HashSet<>(Collections.singletonList("IH_ROL")));
        accesosVoluntarios.put("/cuerpo/coordinacion/periodos",
                new HashSet<>(Collections.singletonList("V_PER")));
        accesosVoluntarios.put("/cuerpo/coordinacion/periodos/nuevo",
                new HashSet<>(Collections.singletonList("C_PER")));
        accesosVoluntarios.put("/cuerpo/coordinacion/periodos/editar",
                new HashSet<>(Collections.singletonList("E_PER")));
        accesosVoluntarios.put("/cuerpo/coordinacion/periodos/in_habilitar",
                new HashSet<>(Collections.singletonList("IH_PER")));
        accesosVoluntarios.put("/cuerpo/coordinacion/personalizacion",
                new HashSet<>(Collections.singletonList("CUSTOM")));
        accesosVoluntarios.put("/cuerpo/actividades",
                new HashSet<>(Collections.singletonList("V_ACT")));
        accesosVoluntarios.put("/cuerpo/actividades/nueva",
                new HashSet<>(Collections.singletonList("C_ACT")));
        accesosVoluntarios.put("/cuerpo/actividades/editar",
                new HashSet<>(Arrays.asList("E_ACT","E_M_ACT")));
        accesosVoluntarios.put("/cuerpo/actividades/eliminar",
                new HashSet<>(Arrays.asList("B_ACT","B_M_ACT")));
        accesosVoluntarios.put("/cuerpo/formaciones",
                new HashSet<>(Collections.singletonList("V_FOR")));
        accesosVoluntarios.put("/cuerpo/formaciones/nueva",
                new HashSet<>(Collections.singletonList("C_FOR")));
        accesosVoluntarios.put("/cuerpo/formaciones/editar",
                new HashSet<>(Arrays.asList("E_FOR","E_M_FOR")));
        accesosVoluntarios.put("/cuerpo/formaciones/eliminar",
                new HashSet<>(Arrays.asList("B_FOR","B_M_FOR")));

        // FormacionesController
        accesosVoluntarios.put("/formaciones/crear",
                new HashSet<>(Collections.singletonList("C_FOR")));
        accesosVoluntarios.put("/formaciones/guardar",
                new HashSet<>(Arrays.asList("E_FOR","E_M_FOR")));
        accesosVoluntarios.put("/formaciones/buscar",
                new HashSet<>(Collections.singletonList("V_FOR")));

        // HomeController
//        accesosVoluntarios.put("/", new HashSet<>(Collections.singletonList("DEFAULT")));
//        accesosVoluntarios.put("/app", new HashSet<>(Collections.singletonList("DEFAULT")));
//        accesosVoluntarios.put("/login", new HashSet<>(Collections.singletonList("DEFAULT")));
//        accesosVoluntarios.put("/index", new HashSet<>(Collections.singletonList("DEFAULT")));
//        accesosVoluntarios.put("/inicio", new HashSet<>(Collections.singletonList("DEFAULT")));
//        accesosVoluntarios.put("/cabecera", new HashSet<>(Collections.singletonList("DEFAULT")));

        // ModalsController
//        accesosVoluntarios.put("/modals/periodo/cambiar", new HashSet<>(Collections.singletonList("DEFAULT")));
        accesosVoluntarios.put("/modals/periodo/in_habilitar",
                new HashSet<>(Collections.singletonList("IH_PER")));
        accesosVoluntarios.put("/modals/actividad/eliminar",
                new HashSet<>(Arrays.asList("B_ACT","B_M_ACT")));
        accesosVoluntarios.put("/modals/formacion/eliminar",
                new HashSet<>(Arrays.asList("B_FOR","B_M_FOR")));
        accesosVoluntarios.put("/modals/voluntario/in_habilitar",
                new HashSet<>(Collections.singletonList("IH_VOL")));
        accesosVoluntarios.put("/modals/rol/in_habilitar",
                new HashSet<>(Collections.singletonList("IH_ROL")));

        // PeriodosController
        accesosVoluntarios.put("/coordinacion/periodos/crear",
                new HashSet<>(Collections.singletonList("C_PER")));
        accesosVoluntarios.put("/coordinacion/periodos/guardar",
                new HashSet<>(Collections.singletonList("E_PER")));
        accesosVoluntarios.put("/coordinacion/periodos/buscar",
                new HashSet<>(Collections.singletonList("V_PER")));

        // PersonalizacionController
        accesosVoluntarios.put("/coordinacion/personalizacion/cambiar",
                new HashSet<>(Collections.singletonList("CUSTOM")));

        // RolesController
        accesosVoluntarios.put("/coordinacion/roles/crear",
                new HashSet<>(Collections.singletonList("C_ROL")));
        accesosVoluntarios.put("/coordinacion/roles/guardar",
                new HashSet<>(Collections.singletonList("E_ROL")));

        // VoluntariosController
        accesosVoluntarios.put("/coordinacion/voluntarios/crear",
                new HashSet<>(Collections.singletonList("C_VOL")));
        accesosVoluntarios.put("/coordinacion/voluntarios/guardar",
                new HashSet<>(Collections.singletonList("E_VOL")));
        accesosVoluntarios.put("/coordinacion/voluntarios/buscar",
                new HashSet<>(Collections.singletonList("V_VOL")));
    }

    private Usuario obtenerUsuario(String usuarioID) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = this.apiUrl + "/usuarios/" + usuarioID;
        ResponseEntity<Usuario> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Usuario>() {
                }
        );
        return response.getBody();
    }

    public Boolean comprobarPermisos(String usuarioID, String url) {
        if (usuarioID != null) {
            // Obtener tipo de usuario
            Usuario usuario = obtenerUsuario(usuarioID);
            String tipo = usuario.getTipo();

            System.out.println("INFO: " + usuario);

            switch (tipo) {
                case "Voluntario":
                    if (url != null) {
                        for (Rol rol : usuario.getRoles()) {
                            List<Permiso> permisos = (List<Permiso>) rol.getPermisos();
//                            permisos.forEach(permiso -> System.out.println(permiso.getNombre()));
                            Set<String> permisosUrl = accesosVoluntarios.get(url);
                            System.out.println("INFO: " + permisosUrl);
                            if (permisosUrl != null) {
                                for (Permiso p : permisos) {
                                    if (permisosUrl.contains(p.getNombre())) {
                                        System.out.println("INFO: " + p.getNombre());
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    break;

                case "Socio":
                    break;
            }
        }

        return false;
    }
}
