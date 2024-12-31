package com.tfg.eldest.rol;

import com.tfg.eldest.usuario.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void guardarRol(Long rolId, Rol rolModificado) {
        Rol rol = getRol(rolId);

        if (rolModificado.getNombre() != null && !rolModificado.getNombre().isEmpty()
                && !rolModificado.getNombre().equals(rol.getNombre())) {
            rol.setNombre(rolModificado.getNombre());
        }
    }
}
