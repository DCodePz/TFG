package com.tfg.eldest.actividadformacion.actividad;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ActividadService {
    private final ActividadRepository actividadRepository;

    @Autowired
    public ActividadService(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    public List<ActividadFormacion> getActividades() {
        return actividadRepository.getActividadesVisibles();
    }

    public ActividadFormacion getActividad(Long actividadId) {
        Optional<ActividadFormacion> actividad = actividadRepository.findActividadById(actividadId);

        if (actividad.isEmpty()) {
            throw new IllegalArgumentException("Actividad no encontrada");
        }

        return actividad.get();
    }

    public List<ActividadFormacion> getActividadesPorPeriodo(Long periodoId) {
        return actividadRepository.getActividadesPorPeriodo(periodoId);
    }

    // TODO: Revisar restricciones de campos que haya que comprobar
    public void crearActividad(ActividadFormacion actividad) {
        if (actividad.getTitulo()==null || actividad.getTitulo().isEmpty()
                || actividad.getEncargados()==null || actividad.getEncargados().isEmpty()
                || actividad.getGrupo_edad()==null || actividad.getGrupo_edad().isEmpty()
                || actividad.getObjetivos()==null || actividad.getObjetivos().isEmpty()
                || actividad.getMateriales()==null || actividad.getMateriales().isEmpty()
                || actividad.getDescripcion()==null || actividad.getDescripcion().isEmpty()
                || actividad.getObservaciones()==null || actividad.getObservaciones().isEmpty()) {
            throw new IllegalStateException("Algún campo es vacio");
        }

        actividad.setId(actividadRepository.count()+1);
        actividad.setTipo("Actividad");
        actividad.setVisible(Boolean.TRUE);
        actividadRepository.save(actividad);
    }

    @Transactional
    public void eliminarActividad(Long actividadId) {
        ActividadFormacion actividad = getActividad(actividadId);

        actividad.setVisible(Boolean.FALSE);
    }

    @Transactional
    public void guardarActividad(Long actividadId, ActividadFormacion actividadModificada) {
        ActividadFormacion actividad = getActividad(actividadId);

        if (actividadModificada.getNumero() != null && !actividadModificada.getNumero().isEmpty()
                && !Objects.equals(actividad.getNumero(), actividadModificada.getNumero())) {
            actividad.setNumero(actividadModificada.getNumero());
        }

        if (actividadModificada.getTitulo() != null && !actividadModificada.getTitulo().isEmpty()
                && !Objects.equals(actividad.getTitulo(), actividadModificada.getTitulo())) {
            actividad.setTitulo(actividadModificada.getTitulo());
        }

        if (actividadModificada.getFech_realiz() != null
                && !Objects.equals(actividad.getFech_realiz(), actividadModificada.getFech_realiz())) {
            actividad.setFech_realiz(actividadModificada.getFech_realiz());
        }

        if (actividadModificada.getEncargados() != null && !actividadModificada.getEncargados().isEmpty()
                && !Objects.equals(actividad.getEncargados(), actividadModificada.getEncargados())) {
            actividad.setEncargados(actividadModificada.getEncargados());
        }

        if (actividadModificada.getNumVoluntarios() != null
                && !Objects.equals(actividad.getNumVoluntarios(), actividadModificada.getNumVoluntarios())) {
            actividad.setNumVoluntarios(actividadModificada.getNumVoluntarios());
        }

        if (actividadModificada.getNumParticipantes() != null
                && !Objects.equals(actividad.getNumParticipantes(), actividadModificada.getNumParticipantes())) {
            actividad.setNumParticipantes(actividadModificada.getNumParticipantes());
        }

        if (actividadModificada.getGrupo_edad() != null && !actividadModificada.getGrupo_edad().isEmpty()
                && !Objects.equals(actividad.getGrupo_edad(), actividadModificada.getGrupo_edad())) {
            actividad.setGrupo_edad(actividadModificada.getGrupo_edad());
        }

        if (actividadModificada.getDuracion() != null
                && !Objects.equals(actividad.getDuracion(), actividadModificada.getDuracion())) {
            actividad.setDuracion(actividadModificada.getDuracion());
        }

        if (actividadModificada.getObjetivos() != null && !actividadModificada.getObjetivos().isEmpty()
                && !Objects.equals(actividad.getObjetivos(), actividadModificada.getObjetivos())) {
            actividad.setObjetivos(actividadModificada.getObjetivos());
        }

        if (actividadModificada.getMateriales() != null && !actividadModificada.getMateriales().isEmpty()
                && !Objects.equals(actividad.getMateriales(), actividadModificada.getMateriales())) {
            actividad.setMateriales(actividadModificada.getMateriales());
        }

        if (actividadModificada.getDescripcion() != null && !actividadModificada.getDescripcion().isEmpty()
                && !Objects.equals(actividad.getDescripcion(), actividadModificada.getDescripcion())) {
            actividad.setDescripcion(actividadModificada.getDescripcion());
        }

        if (actividadModificada.getObservaciones() != null && !actividadModificada.getObservaciones().isEmpty()
                && !Objects.equals(actividad.getObservaciones(), actividadModificada.getObservaciones())) {
            actividad.setObservaciones(actividadModificada.getObservaciones());
        }
    }

    public List<ActividadFormacion> getActividadesBusqueda(String infoBuscar, Long periodoId) {
        return actividadRepository.getActividadesBusqueda(infoBuscar, periodoId);
    }
}
