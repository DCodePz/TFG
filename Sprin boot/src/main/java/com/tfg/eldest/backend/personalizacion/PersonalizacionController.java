package com.tfg.eldest.backend.personalizacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/personalizacion")
public class PersonalizacionController {
    private final PersonalizacionService personalizacionService;

    @Autowired
    public PersonalizacionController(PersonalizacionService personalizacionService) {
        this.personalizacionService = personalizacionService;
    }

    // GET
    @GetMapping()
    public Personalizacion personalizacion() {
        return personalizacionService.getPersonalizacion();
    }

    // PUT
    @PutMapping(path = "guardar")
    public void guardarPersonalizacion(@RequestBody(required = false) Personalizacion personalizacionModificada) {
        personalizacionService.guardarPersonalizacion(personalizacionModificada);
    }
}
