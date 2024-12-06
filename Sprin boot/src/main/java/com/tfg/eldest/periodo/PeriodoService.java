package com.tfg.eldest.periodo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PeriodoService {
    private final PeriodoRepository periodoRepository;

    @Autowired
    public PeriodoService(PeriodoRepository periodoRepository) {
        this.periodoRepository = periodoRepository;
    }

    public List<Periodo> getPeriodos() {
        return periodoRepository.findAll();
    }

    public Periodo getPeriodo(Long periodoId) {
        return periodoRepository.findById(periodoId)
                .orElseThrow(() -> new RuntimeException("Periodo no encontrado"));
    }

    public void crearPeriodo(Periodo periodo) {
        if (periodo.getNombre().isEmpty()) {
            throw new RuntimeException("Periodo nombre no puede estar vacio");
        }

        periodo.setId(periodoRepository.count() + 1);
        periodo.setHabilitado(Boolean.TRUE);
        periodoRepository.save(periodo);
    }

    @Transactional
    public void invertirHabilitado(Long periodoId) {
        Periodo periodo = getPeriodo(periodoId);
        periodo.setHabilitado(!periodo.getHabilitado());
    }

    @Transactional
    public void guardarPeriodo(Long periodoId, Periodo periodoModificado) {
        Periodo periodo = getPeriodo(periodoId);

        if (periodoModificado.getNombre() != null && !periodoModificado.getNombre().isEmpty()
                && !Objects.equals(periodo.getNombre(), periodoModificado.getNombre())) {
            periodo.setNombre(periodoModificado.getNombre());
        }

        if (periodoModificado.getInicio() != null
                && !Objects.equals(periodo.getInicio(), periodoModificado.getInicio())) {
            periodo.setInicio(periodoModificado.getInicio());
        }

        if (periodoModificado.getFin() != null
                && !Objects.equals(periodo.getFin(), periodoModificado.getFin())) {
            periodo.setFin(periodoModificado.getFin());
        }

        periodo.setHabilitado(periodoModificado.getHabilitado());
    }
}
