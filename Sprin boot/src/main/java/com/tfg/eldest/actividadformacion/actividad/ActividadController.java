package com.tfg.eldest.actividadformacion.actividad;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Restricciones de permisos

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

    // POST
    @PostMapping(path = "crear")
    public void crearActividad(@RequestBody ActividadFormacion actividad) {
        actividadService.crearActividad(actividad);
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
}
