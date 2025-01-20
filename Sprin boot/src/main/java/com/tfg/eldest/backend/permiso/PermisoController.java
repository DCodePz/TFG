package com.tfg.eldest.backend.permiso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/permisos")
public class PermisoController {
    private final PermisoService permisoService;

    @Autowired
    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    // GET
    @GetMapping
    public List<Permiso> getPermisos() {
        return permisoService.getPermisos();
    }

    @GetMapping(path = "{permisoId}")
    public Permiso getPermiso(@PathVariable Long permisoId) {
        return permisoService.getPermiso(permisoId);
    }
}
