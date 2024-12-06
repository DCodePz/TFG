package com.tfg.eldest.actividadformacion.actividad;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActividadRepository extends JpaRepository<ActividadFormacion, Long> {
    @Query("SELECT af FROM ActividadFormacion af WHERE af.tipo = 'Actividad' AND af.visible = true")
    List<ActividadFormacion> getActividadesVisibles();

    @Query("SELECT af FROM ActividadFormacion af WHERE af.tipo = 'Actividad' AND af.id = ?1")
    Optional<ActividadFormacion> findActividadById(Long actividadId);
}
