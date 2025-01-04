package com.tfg.eldest.backend.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET
    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping(path = "{usuarioId}")
    public Usuario getUsuario(@PathVariable Long usuarioId) {
        return usuarioService.getUsuario(usuarioId);
    }

    @GetMapping(path = "voluntarios")
    public List<Usuario> getVoluntarios() {
        return usuarioService.getVoluntarios();
    }

    @GetMapping(path = "voluntarios/habilitados")
    public List<Usuario> getVoluntariosHabilitados() {
        return usuarioService.getVoluntariosHabilitados();
    }

    @GetMapping(path = "voluntarios/buscar/{infoBuscar}")
    public List<Usuario> getVoluntariosBusqueda(@PathVariable String infoBuscar) {
        return usuarioService.getVoluntariosBusqueda(infoBuscar);
    }

    @GetMapping(path = "{usuarioId}/existe")
    public Boolean existeUsuario(@PathVariable Long usuarioId) {
        return usuarioService.existeUsuario(usuarioId);
    }

    // POST
    @PostMapping(path = "crear")
    public void crearUsuario(@RequestBody Usuario usuario) {
        usuarioService.crearUsuario(usuario);
    }

    // PUT
    @PutMapping(path = "invertirHabilitado/{usuarioId}")
    public void invertirHabilitado(@PathVariable Long usuarioId) {
        usuarioService.invertirHabilitado(usuarioId);
    }

    @PutMapping(path = "guardar/{usuarioId}")
    public void guardarUsuario(@PathVariable Long usuarioId,
                               @RequestBody(required = false) Usuario usuarioModificado) {
        usuarioService.guardarUsuario(usuarioId, usuarioModificado);
    }
}
