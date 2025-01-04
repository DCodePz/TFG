package com.tfg.eldest.usuario;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Usuario> getVoluntariosHabilitados() {
        return usuarioRepository.getVoluntariosHabilitados();
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

        if (usuarioModificado.getId() != null
                && !usuarioModificado.getId().equals(usuario.getId())) {
            usuario.setId(usuarioModificado.getId());
        }

        if (usuarioModificado.getNombre() != null && !usuarioModificado.getNombre().isEmpty()
                && !usuarioModificado.getNombre().equals(usuario.getNombre())) {
            usuario.setNombre(usuarioModificado.getNombre());
        }

        if (usuarioModificado.getApellidos() != null && !usuarioModificado.getApellidos().isEmpty()
                && !usuarioModificado.getApellidos().equals(usuario.getApellidos())) {
            usuario.setApellidos(usuarioModificado.getApellidos());
        }

        if (usuarioModificado.getEmail() != null && !usuarioModificado.getEmail().isEmpty()
                && !usuarioModificado.getEmail().equals(usuario.getEmail())) {
            usuario.setEmail(usuarioModificado.getEmail());
        }

        if (usuarioModificado.getPassword() != null && !usuarioModificado.getPassword().isEmpty()
                && !usuarioModificado.getPassword().equals(usuario.getPassword())) {
            usuario.setPassword(usuarioModificado.getPassword());
        }

        if (usuarioModificado.getRoles() != null && !usuarioModificado.getRoles().isEmpty()
                && !usuarioModificado.getRoles().equals(usuario.getRoles())) {
            usuario.setRoles(usuarioModificado.getRoles());
        }
    }

    public List<Usuario> getVoluntariosBusqueda(String infoBuscar) {
        return usuarioRepository.getVoluntariosBusqueda(infoBuscar);
    }

    public Boolean existeUsuario(Long usuarioId) {
        Object tmp = usuarioRepository.getUsuario(usuarioId);
        System.out.println(tmp);
        return tmp != null;
    }
}
