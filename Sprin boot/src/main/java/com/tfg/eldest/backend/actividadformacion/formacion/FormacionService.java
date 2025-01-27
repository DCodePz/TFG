package com.tfg.eldest.backend.actividadformacion.formacion;

import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
import com.tfg.eldest.backend.periodo.PeriodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FormacionService {
    private final FormacionRepository formacionRepository;
    private final PeriodoRepository periodoRepository;

    @Autowired
    public FormacionService(FormacionRepository formacionRepository, PeriodoRepository periodoRepository) {
        this.formacionRepository = formacionRepository;
        this.periodoRepository = periodoRepository;
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

    public void crearFormacion(ActividadFormacion formacion, Long periodoId) {
        if (formacion.getNumero() == null || formacion.getNumero().isEmpty()) {
            throw new IllegalStateException("Campo número vacio");
        }
        if (formacion.getTitulo() == null || formacion.getTitulo().isEmpty()) {
            throw new IllegalStateException("Campo título es vacio");
        }
        if (formacion.getFech_realiz() == null) {
            throw new IllegalStateException("Fampo fecha es vacio");
        }
        if (formacion.getEncargados() == null || formacion.getEncargados().isEmpty()) {
            throw new IllegalStateException("Campo encargados es vacio");
        }
        if (formacion.getNumVoluntarios() == null) {
            throw new IllegalStateException("Campo numvoluntarios es vacio");
        }
        if (formacion.getNumParticipantes() == null) {
            throw new IllegalStateException("Campo numparticipantes es vacio");
        }
        if (formacion.getGrupo_edad() == null || formacion.getGrupo_edad().isEmpty()) {
            throw new IllegalStateException("Campo grupoedad es vacio");
        }
        if (formacion.getDuracion() == null) {
            throw new IllegalStateException("Campo duración es vacio");
        }
        if (formacion.getObjetivos() == null || formacion.getObjetivos().isEmpty()) {
            throw new IllegalStateException("Campo objetivos es vacio");
        }
        if (formacion.getMateriales() == null || formacion.getMateriales().isEmpty()) {
            throw new IllegalStateException("Campo materiales es vacio");
        }
        if (formacion.getDescripcion() == null || formacion.getDescripcion().isEmpty()) {
            throw new IllegalStateException("Campo descripción es vacio");
        }
        if (formacion.getObservaciones() == null || formacion.getObservaciones().isEmpty()) {
            throw new IllegalStateException("Campo observaciones es vacio");
        }

        formacion.setTipo("Formacion");
        formacion.setVisible(Boolean.TRUE);
        formacion.setPeriodo(periodoRepository.findById(periodoId).get());
        formacionRepository.save(formacion);
    }

    @Transactional
    public void eliminarFormacion(Long formacionId) {
        ActividadFormacion formacion = getFormacion(formacionId);

        formacion.setVisible(Boolean.FALSE);
    }

    @Transactional
    public void guardarFormacion(Long formacionId, ActividadFormacion formacionModificada) {
        ActividadFormacion formacion = getFormacion(formacionId);

        if (formacionModificada.getNumero() != null && !formacionModificada.getNumero().isEmpty()
                && !Objects.equals(formacion.getNumero(), formacionModificada.getNumero())) {
            formacion.setNumero(formacionModificada.getNumero());
        }

        if (formacionModificada.getTitulo() != null && !formacionModificada.getTitulo().isEmpty()
                && !Objects.equals(formacion.getTitulo(), formacionModificada.getTitulo())) {
            formacion.setTitulo(formacionModificada.getTitulo());
        }

        if (formacionModificada.getFech_realiz() != null
                && !Objects.equals(formacion.getFech_realiz(), formacionModificada.getFech_realiz())) {
            formacion.setFech_realiz(formacionModificada.getFech_realiz());
        }

        if (formacionModificada.getEncargados() != null && !formacionModificada.getEncargados().isEmpty()
                && !Objects.equals(formacion.getEncargados(), formacionModificada.getEncargados())) {
            formacion.setEncargados(formacionModificada.getEncargados());
        }

        if (formacionModificada.getNumVoluntarios() != null
                && !Objects.equals(formacion.getNumVoluntarios(), formacionModificada.getNumVoluntarios())) {
            formacion.setNumVoluntarios(formacionModificada.getNumVoluntarios());
        }

        if (formacionModificada.getNumParticipantes() != null
                && !Objects.equals(formacion.getNumParticipantes(), formacionModificada.getNumParticipantes())) {
            formacion.setNumParticipantes(formacionModificada.getNumParticipantes());
        }

        if (formacionModificada.getGrupo_edad() != null && !formacionModificada.getGrupo_edad().isEmpty()
                && !Objects.equals(formacion.getGrupo_edad(), formacionModificada.getGrupo_edad())) {
            formacion.setGrupo_edad(formacionModificada.getGrupo_edad());
        }

        if (formacionModificada.getDuracion() != null
                && !Objects.equals(formacion.getDuracion(), formacionModificada.getDuracion())) {
            formacion.setDuracion(formacionModificada.getDuracion());
        }

        if (formacionModificada.getObjetivos() != null && !formacionModificada.getObjetivos().isEmpty()
                && !Objects.equals(formacion.getObjetivos(), formacionModificada.getObjetivos())) {
            formacion.setObjetivos(formacionModificada.getObjetivos());
        }

        if (formacionModificada.getMateriales() != null && !formacionModificada.getMateriales().isEmpty()
                && !Objects.equals(formacion.getMateriales(), formacionModificada.getMateriales())) {
            formacion.setMateriales(formacionModificada.getMateriales());
        }

        if (formacionModificada.getDescripcion() != null && !formacionModificada.getDescripcion().isEmpty()
                && !Objects.equals(formacion.getDescripcion(), formacionModificada.getDescripcion())) {
            formacion.setDescripcion(formacionModificada.getDescripcion());
        }

        if (formacionModificada.getObservaciones() != null && !formacionModificada.getObservaciones().isEmpty()
                && !Objects.equals(formacion.getObservaciones(), formacionModificada.getObservaciones())) {
            formacion.setObservaciones(formacionModificada.getObservaciones());
        }
    }

    public List<ActividadFormacion> getFormacionesBusqueda(String infoBuscar, Long periodoId) {
        return formacionRepository.getFormacionesBusqueda(infoBuscar, periodoId);
    }
}
