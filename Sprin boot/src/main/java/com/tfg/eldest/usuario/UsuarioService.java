package com.tfg.eldest.usuario;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public List<Usuario> getVoluntarios() {
        return usuarioRepository.getVoluntarios();
    }

    // TODO: Revisar restricciones de campos que haya que comprobar
    public void crearUsuario(Usuario usuario) {
        if (usuario.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Algún campo es vacio");
        }

        usuario.setHabilitado(Boolean.TRUE);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void invertirHabilitado(Long usuarioId) {
        Usuario usuario = getUsuario(usuarioId);
        usuario.setHabilitado(!usuario.getHabilitado());
    }

    @Transactional
    public void guardarUsuario(Long usuarioId, Usuario usuarioModificado) {
        Usuario usuario = getUsuario(usuarioId);


        if (usuarioModificado.getPassword() != null && !usuarioModificado.getPassword().isEmpty()
                && !Objects.equals(usuario.getPassword(), usuarioModificado.getPassword())) {
            usuario.setPassword(usuarioModificado.getPassword());
        }

        usuario.setHabilitado(usuarioModificado.getHabilitado());
    }

    public List<Usuario> getVoluntariosBusqueda(String infoBuscar) {
        return usuarioRepository.getVoluntariosBusqueda(infoBuscar);
    }
}
