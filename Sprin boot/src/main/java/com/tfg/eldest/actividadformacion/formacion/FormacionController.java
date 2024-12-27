package com.tfg.eldest.actividadformacion.formacion;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Restricciones de permisos

@RestController
@RequestMapping(path = "api/v1/formaciones")
public class FormacionController {
    private final FormacionService formacionService;

    @Autowired
    public FormacionController(FormacionService formacionService) {
        this.formacionService = formacionService;
    }

    // GET
    @GetMapping
    public List<ActividadFormacion> getFormaciones() {
        return formacionService.getFormaciones();
    }

    @GetMapping(path = "{formacionId}")
    public ActividadFormacion getFormacion(@PathVariable Long formacionId) {
        return formacionService.getFormacion(formacionId);
    }

    @GetMapping(path = "periodo/{periodoId}")
    public List<ActividadFormacion> getFormacionesPorPeriodo(@PathVariable Long periodoId) {
        return formacionService.getFormacionesPorPeriodo(periodoId);
    }

    @GetMapping(path = "buscar/{infoBuscar}/{periodoId}")
    public List<ActividadFormacion> getFormacionesBusqueda(@PathVariable String infoBuscar, @PathVariable Long periodoId) {
        return formacionService.getFormacionesBusqueda(infoBuscar, periodoId);
    }

    // POST
    @PostMapping(path = "crear/{periodoId}")
    public void crearFormacion(@PathVariable Long periodoId, @RequestBody ActividadFormacion formacion) {
        formacionService.crearFormacion(formacion, periodoId);
    }

    // PUT
    @PutMapping(path = "eliminar/{formacionId}")
    public void eliminarFormacion(@PathVariable Long formacionId) {
        formacionService.eliminarFormacion(formacionId);
    }

    @PutMapping(path = "guardar/{formacionId}")
    public void guardarFormacion(@PathVariable Long formacionId,
                                 @RequestBody(required = false) ActividadFormacion formacionModificada) {
        formacionService.guardarFormacion(formacionId, formacionModificada);
    }
}
