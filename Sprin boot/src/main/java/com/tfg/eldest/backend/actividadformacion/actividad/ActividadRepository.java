package com.tfg.eldest.backend.actividadformacion.actividad;

import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActividadRepository extends JpaRepository<ActividadFormacion, Long> {
    @Query("SELECT af " +
            "FROM ActividadFormacion af " +
            "WHERE af.tipo = 'Actividad' " +
            "AND af.visible = true")
    List<ActividadFormacion> getActividadesVisibles();

    @Query("SELECT af " +
            "FROM ActividadFormacion af " +
            "WHERE af.tipo = 'Actividad' " +
            "AND af.visible = true " +
            "AND af.periodo.id = ?1")
    List<ActividadFormacion> getActividadesPorPeriodo(Long periodoId);

    @Query("SELECT af " +
            "FROM ActividadFormacion af " +
            "WHERE af.tipo = 'Actividad' " +
            "AND af.id = ?1")
    Optional<ActividadFormacion> findActividadById(Long actividadId);

    @Query("SELECT af " +
            "FROM ActividadFormacion af " +
            "WHERE af.tipo = 'Actividad' " +
            "AND af.visible = true " +
            "AND af.periodo.id = ?2 " +
            "AND (af.numero ILIKE CONCAT('%', ?1, '%') " +
            "OR af.titulo ILIKE CONCAT('%', ?1, '%') " +
            "OR af.descripcion ILIKE CONCAT('%', ?1, '%')" +
            "OR af.grupo_edad ILIKE CONCAT('%', ?1, '%'))")
    List<ActividadFormacion> getActividadesBusqueda(String infoBuscar, Long periodoId);
}
