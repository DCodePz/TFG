package com.tfg.eldest.backend.personalizacion;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class PersonalizacionService {
    private final PersonalizacionRepository personalizacionRepository;

    @Autowired
    public PersonalizacionService(PersonalizacionRepository personalizacionRepository) {
        this.personalizacionRepository = personalizacionRepository;
    }

    public Personalizacion getPersonalizacion() {
        return personalizacionRepository.findAll().getFirst();
    }

    @Transactional
    public void guardarPersonalizacion(Personalizacion personalizacionModificada) {
        Personalizacion personalizacion = personalizacionRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Personalizacion no encontrada"));

        if (personalizacionModificada.getNombre() != null && !personalizacionModificada.getNombre().isEmpty()
                && !Objects.equals(personalizacion.getNombre(), personalizacionModificada.getNombre())) {
            personalizacion.setNombre(personalizacionModificada.getNombre());
        }

        if (personalizacionModificada.getColor() != null && !personalizacionModificada.getColor().isEmpty()
                && !Objects.equals(personalizacion.getColor(), personalizacionModificada.getColor())) {
            personalizacion.setColor(personalizacionModificada.getColor());
        }

        if (personalizacionModificada.getFoto() != null
                && !Objects.equals(personalizacion.getFoto(), personalizacionModificada.getFoto())) {
            personalizacion.setFoto(personalizacionModificada.getFoto());
        }
    }
}
