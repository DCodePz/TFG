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

    // POST
    @PostMapping(path = "crear")
    public void crearFormacion(@RequestBody ActividadFormacion formacion) {
        formacionService.crearFormacion(formacion);
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
