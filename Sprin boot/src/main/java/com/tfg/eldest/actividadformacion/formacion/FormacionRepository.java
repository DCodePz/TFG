package com.tfg.eldest.actividadformacion.formacion;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormacionRepository extends JpaRepository<ActividadFormacion, Long> {
    @Query("SELECT af FROM ActividadFormacion af WHERE af.tipo = 'Formacion' AND af.visible = true")
    List<ActividadFormacion> getFormacionesVisibles();

    @Query("SELECT af FROM ActividadFormacion af WHERE af.tipo = 'Formacion' AND af.id = ?1")
    Optional<ActividadFormacion> findFormacionById(Long formacionId);
}
