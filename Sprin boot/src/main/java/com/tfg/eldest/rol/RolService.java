package com.tfg.eldest.rol;

import com.tfg.eldest.permiso.Permiso;
import com.tfg.eldest.usuario.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class RolService {
    private final RolRepository rolRepository;

    @Autowired
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> getRoles() {
        return rolRepository.findAll();
    }

    public List<Rol> getRolesHabilitados() {
        return rolRepository.getRolesHabilitados();
    }

    public Rol getRol(Long rolId) {
        return rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    public void crearRol(Rol rol) {
        if (rol.getNombre().isEmpty()) {
            throw new IllegalArgumentException("Algún campo es vacio");
        }

        rolRepository.save(rol);
    }

    @Transactional
    public void invertirHabilitado(Long rolId) {
        Rol rol = getRol(rolId);
        String nombre = rol.getNombre();
        Boolean habilitado = rol.getHabilitado();
        Collection<Permiso> permisos = rol.getPermisos();
        rolRepository.delete(rol);
        Rol nuevoRol = new Rol(nombre, !habilitado, permisos);
        rolRepository.save(nuevoRol);
    }

    @Transactional
    public void guardarRol(Long rolId, Rol rolModificado) {
        Rol rol = getRol(rolId);

        if (rolModificado.getNombre() != null && !rolModificado.getNombre().isEmpty()
                && !rolModificado.getNombre().equals(rol.getNombre())) {
            rol.setNombre(rolModificado.getNombre());
        }

        if (rolModificado.getPermisos() != null && !rolModificado.getPermisos().isEmpty()
                && !rolModificado.getPermisos().equals(rol.getPermisos())) {
            rol.setPermisos(rolModificado.getPermisos());
        }
    }

    public List<Permiso> permisos(Long rolId) {
        Rol rol = getRol(rolId);

        return List.copyOf(rol.getPermisos());
    }
}
