package com.tfg.eldest.periodo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {
    @Query("SELECT p " +
            "FROM Periodo p " +
            "WHERE p.nombre ILIKE CONCAT('%', ?1, '%')")
    List<Periodo> getPeriodosBusqueda(String infoBuscar);
}
