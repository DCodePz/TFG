package com.tfg.eldest.actividadformacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

import static java.lang.Boolean.TRUE;

@Entity
@Table
public class ActividadFormacion {
    @Id
    private Long id;
    private String tipo;
    private String titulo;
    private LocalDate fech_realiz;
    private String encargados;
    private Integer numVoluntarios;
    private Integer numParticipantes;
    private String grupo_edad;
    private Integer duracion;
    private String objetivos;
    private String materiales;
    private String descripcion;
    private String observaciones;
    private Boolean visible;

    public ActividadFormacion() {
    }

    public ActividadFormacion(Long id, String tipo, String titulo, LocalDate fech_realiz, String encargados, Integer numVoluntarios,
                              Integer numParticipantes, String grupo_edad, Integer duracion, String objetivos,
                              String materiales, String descripcion, String observaciones) {
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
        this.fech_realiz = fech_realiz;
        this.encargados = encargados;
        this.numVoluntarios = numVoluntarios;
        this.numParticipantes = numParticipantes;
        this.grupo_edad = grupo_edad;
        this.duracion = duracion;
        this.objetivos = objetivos;
        this.materiales = materiales;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.visible = TRUE;
    }

//    -- Getters y Setters --

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFech_realiz() {
        return fech_realiz;
    }

    public void setFech_realiz(LocalDate fech_realiz) {
        this.fech_realiz = fech_realiz;
    }

    public String getEncargados() {
        return encargados;
    }

    public void setEncargados(String encargados) {
        this.encargados = encargados;
    }

    public Integer getNumVoluntarios() {
        return numVoluntarios;
    }

    public void setNumVoluntarios(Integer numVoluntarios) {
        this.numVoluntarios = numVoluntarios;
    }

    public Integer getNumParticipantes() {
        return numParticipantes;
    }

    public void setNumParticipantes(Integer numParticipantes) {
        this.numParticipantes = numParticipantes;
    }

    public String getGrupo_edad() {
        return grupo_edad;
    }

    public void setGrupo_edad(String grupo_edad) {
        this.grupo_edad = grupo_edad;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

//    -----------------------

    @Override
    public String toString() {
        return "ActividadFormacion{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", fech_realiz=" + fech_realiz +
                ", encargados='" + encargados + '\'' +
                ", numVoluntarios=" + numVoluntarios +
                ", numParticipantes=" + numParticipantes +
                ", grupo_edad='" + grupo_edad + '\'' +
                ", duracion=" + duracion +
                ", objetivos='" + objetivos + '\'' +
                ", materiales='" + materiales + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", visible=" + visible +
                '}';
    }
}
