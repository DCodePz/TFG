package com.tfg.eldest.rol;

import com.tfg.eldest.permiso.Permiso;
import com.tfg.eldest.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/roles")
public class RolController {
    private final RolService rolService;

    @Autowired
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    // GET
    @GetMapping
    public List<Rol> getRoles() {
        return rolService.getRoles();
    }

    @GetMapping(path = "habilitados")
    public List<Rol> getRolesHabilitados() {
        return rolService.getRolesHabilitados();
    }

    @GetMapping(path = "{rolId}")
    public Rol getRol(@PathVariable Long rolId) {
        return rolService.getRol(rolId);
    }

    @GetMapping(path = "{rolId}/permisos")
    public List<Permiso> permisos(@PathVariable Long rolId) {
        return rolService.permisos(rolId);
    }

    // POST
    @PostMapping(path = "crear")
    public void crearRol(@RequestBody Rol rol) {
        rolService.crearRol(rol);
    }

    // PUT
    @PutMapping(path = "invertirHabilitado/{rolId}")
    public void invertirHabilitado(@PathVariable Long rolId) {
        rolService.invertirHabilitado(rolId);
    }

    @PutMapping(path = "guardar/{rolId}")
    public void guardarRol(@PathVariable Long rolId,
                               @RequestBody(required = false) Rol rolModificado) {
        rolService.guardarRol(rolId, rolModificado);
    }
}
