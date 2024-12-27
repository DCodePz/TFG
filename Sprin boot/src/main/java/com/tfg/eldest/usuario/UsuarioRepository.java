package com.tfg.eldest.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u " +
            "FROM Usuario u " +
            "WHERE u.tipo = 'Voluntario' ")
    List<Usuario> getVoluntarios();

    @Query("SELECT u " +
            "FROM Usuario u " +
            "WHERE u.tipo = 'Voluntario' " +
            "AND (CAST(u.id AS string) ILIKE CONCAT('%', ?1, '%') " +
            "OR u.nombre ILIKE CONCAT('%', ?1, '%'))")
    List<Usuario> getVoluntariosBusqueda(String infoBuscar);
}
