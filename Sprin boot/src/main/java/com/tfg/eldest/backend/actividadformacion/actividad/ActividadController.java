package com.tfg.eldest.backend.actividadformacion.actividad;

import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/actividades")
public class ActividadController {
    private final ActividadService actividadService;

    @Autowired
    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    // GET
    @GetMapping
    public List<ActividadFormacion> getActividades() {
        return actividadService.getActividades();
    }

    @GetMapping(path = "{actividadId}")
    public ActividadFormacion getActividad(@PathVariable Long actividadId) {
        return actividadService.getActividad(actividadId);
    }

    @GetMapping(path = "periodo/{periodoId}")
    public List<ActividadFormacion> getActividadesPorPeriodo(@PathVariable Long periodoId) {
        return actividadService.getActividadesPorPeriodo(periodoId);
    }

    @GetMapping(path = "buscar/{infoBuscar}/{periodoId}")
    public List<ActividadFormacion> getActividadesBusqueda(@PathVariable String infoBuscar, @PathVariable Long periodoId) {
        return actividadService.getActividadesBusqueda(infoBuscar, periodoId);
    }

    // POST
    @PostMapping(path = "crear/{periodoId}")
    public void crearActividad(@PathVariable Long periodoId,
                               @RequestBody ActividadFormacion actividad) {
        actividadService.crearActividad(actividad, periodoId);
    }

    // PUT
    @PutMapping(path = "eliminar/{actividadId}")
    public void eliminarActividad(@PathVariable Long actividadId) {
        actividadService.eliminarActividad(actividadId);
    }

    @PutMapping(path = "guardar/{actividadId}")
    public void guardarActividad(@PathVariable Long actividadId,
                                 @RequestBody(required = false) ActividadFormacion actividadModificada) {
        actividadService.guardarActividad(actividadId, actividadModificada);
    }

    // TODO: Evaluar actividad
    // (path = "evaluar/{actividadId}")
}
