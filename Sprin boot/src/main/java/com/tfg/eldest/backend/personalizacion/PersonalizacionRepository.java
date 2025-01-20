package com.tfg.eldest.backend.personalizacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalizacionRepository extends JpaRepository<Personalizacion, Long> {
}
