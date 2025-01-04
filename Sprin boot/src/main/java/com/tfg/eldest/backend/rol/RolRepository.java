package com.tfg.eldest.backend.rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    @Query("SELECT r " +
            "FROM Rol r " +
            "WHERE r.habilitado = true")
    List<Rol> getRolesHabilitados();
}
