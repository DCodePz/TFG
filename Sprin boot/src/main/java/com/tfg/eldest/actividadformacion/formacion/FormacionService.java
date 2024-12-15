package com.tfg.eldest.actividadformacion.formacion;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FormacionService {
    private final FormacionRepository formacionRepository;

    @Autowired
    public FormacionService(FormacionRepository formacionRepository) {
        this.formacionRepository = formacionRepository;
    }

    public List<ActividadFormacion> getFormaciones() {
        return formacionRepository.getFormacionesVisibles();
    }

    public ActividadFormacion getFormacion(Long formacionId) {
        Optional<ActividadFormacion> formacion = formacionRepository.findFormacionById(formacionId);

        if (formacion.isEmpty()) {
            throw new IllegalArgumentException("Formacion no encontrada");
        }

        return formacion.get();
    }

    public List<ActividadFormacion> getFormacionesPorPeriodo(Long periodoId) {
        return formacionRepository.getFormacionesPorPeriodo(periodoId);
    }

    // TODO: Revisar restricciones de campos que haya que comprobar
    public void crearFormacion(ActividadFormacion formacion) {
        if (formacion.getTitulo().isEmpty() || formacion.getEncargados().isEmpty()
                || formacion.getGrupo_edad().isEmpty() || formacion.getObjetivos().isEmpty()
                || formacion.getMateriales().isEmpty() || formacion.getDescripcion().isEmpty()
                || formacion.getObservaciones().isEmpty()) {
            throw new IllegalStateException("Algun campo es vacio");
        }

        formacion.setId(formacionRepository.count()+1);
        formacion.setTipo("Formacion");
        formacion.setVisible(Boolean.TRUE);
        formacionRepository.save(formacion);
    }

    @Transactional
    public void eliminarFormacion(Long formacionId) {
        ActividadFormacion formacion = getFormacion(formacionId);

        formacion.setVisible(Boolean.FALSE);
    }

    @Transactional
    public void guardarFormacion(Long formacionId, ActividadFormacion actividadModificada) {
        ActividadFormacion formacion = getFormacion(formacionId);

        if (actividadModificada.getTitulo() != null && !actividadModificada.getTitulo().isEmpty()
                && !Objects.equals(formacion.getTitulo(), actividadModificada.getTitulo())) {
            formacion.setTitulo(actividadModificada.getTitulo());
        }

        if (actividadModificada.getFech_realiz() != null
                && !Objects.equals(formacion.getFech_realiz(), actividadModificada.getFech_realiz())) {
            formacion.setFech_realiz(actividadModificada.getFech_realiz());
        }

        if (actividadModificada.getEncargados() != null && !actividadModificada.getEncargados().isEmpty()
                && !Objects.equals(formacion.getEncargados(), actividadModificada.getEncargados())) {
            formacion.setEncargados(actividadModificada.getEncargados());
        }

        if (actividadModificada.getNumVoluntarios() != null
                && !Objects.equals(formacion.getNumVoluntarios(), actividadModificada.getNumVoluntarios())) {
            formacion.setNumVoluntarios(actividadModificada.getNumVoluntarios());
        }

        if (actividadModificada.getNumParticipantes() != null
                && !Objects.equals(formacion.getNumParticipantes(), actividadModificada.getNumParticipantes())) {
            formacion.setNumParticipantes(actividadModificada.getNumParticipantes());
        }

        if (actividadModificada.getGrupo_edad() != null && !actividadModificada.getGrupo_edad().isEmpty()
                && !Objects.equals(formacion.getGrupo_edad(), actividadModificada.getGrupo_edad())) {
            formacion.setGrupo_edad(actividadModificada.getGrupo_edad());
        }

        if (actividadModificada.getDuracion() != null
                && !Objects.equals(formacion.getDuracion(), actividadModificada.getDuracion())) {
            formacion.setDuracion(actividadModificada.getDuracion());
        }

        if (actividadModificada.getObjetivos() != null && !actividadModificada.getObjetivos().isEmpty()
                && !Objects.equals(formacion.getObjetivos(), actividadModificada.getObjetivos())) {
            formacion.setObjetivos(actividadModificada.getObjetivos());
        }

        if (actividadModificada.getMateriales() != null && !actividadModificada.getMateriales().isEmpty()
                && !Objects.equals(formacion.getMateriales(), actividadModificada.getMateriales())) {
            formacion.setMateriales(actividadModificada.getMateriales());
        }

        if (actividadModificada.getDescripcion() != null && !actividadModificada.getDescripcion().isEmpty()
                && !Objects.equals(formacion.getDescripcion(), actividadModificada.getDescripcion())) {
            formacion.setDescripcion(actividadModificada.getDescripcion());
        }

        if (actividadModificada.getObservaciones() != null && !actividadModificada.getObservaciones().isEmpty()
                && !Objects.equals(formacion.getObservaciones(), actividadModificada.getObservaciones())) {
            formacion.setObservaciones(actividadModificada.getObservaciones());
        }
    }

    public List<ActividadFormacion> getFormacionesBusqueda(String infoBuscar, Long periodoId) {
        return formacionRepository.getFormacionesBusqueda(infoBuscar, periodoId);
    }
}
