package com.tfg.eldest.backend.actividadformacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tfg.eldest.backend.periodo.Periodo;
import com.tfg.eldest.backend.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table
public class ActividadFormacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tipo;
    private String numero;
    private String titulo;
    private LocalDate fech_realiz;

    @ManyToMany
    @JoinTable(
            name = "actividadformacion_usuarios_preparacion",
            joinColumns = @JoinColumn(name = "actividadformacion_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Collection<Usuario> encargados;

    @ManyToMany
    @JoinTable(
            name = "actividadformacion_usuarios_participacion",
            joinColumns = @JoinColumn(
                    name = "actividadformacion_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "usuario_id"
            )
    )
    private Collection<Usuario> participantes;

    private Integer numVoluntarios;
    private Integer numParticipantes;
    private String grupo_edad;
    private Integer duracion;
    private String objetivos;
    private String materiales;
    private String descripcion;
    private String observaciones;
    private Boolean visible;

    @ManyToOne
    @JoinColumn(name = "periodo_id")
    @JsonBackReference
    private Periodo periodo;


    public ActividadFormacion() {
    }

    public ActividadFormacion(String tipo, String numero, String titulo, LocalDate fech_realiz, Collection<Usuario> encargados,
                              Integer numVoluntarios, Integer numParticipantes, String grupo_edad, Integer duracion,
                              String objetivos, String materiales, String descripcion, String observaciones,
                              Boolean visible, Periodo periodo) {
        this.tipo = tipo;
        this.numero = numero;
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
        this.visible = visible;
        this.periodo = periodo;
    }

    public ActividadFormacion(String numero, String titulo, LocalDate fech_realiz, Collection<Usuario> encargados,
                              Integer numVoluntarios, Integer numParticipantes, String grupo_edad, Integer duracion,
                              String objetivos, String materiales, String descripcion, String observaciones,
                              Periodo periodo) {
        this.numero = numero;
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
        this.periodo = periodo;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public Collection<Usuario> getEncargados() {
        return encargados;
    }

    public void setEncargados(Collection<Usuario> encargados) {
        this.encargados = encargados;
    }

    public Collection<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Collection<Usuario> participantes) {
        this.participantes = participantes;
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

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    //    -----------------------

    @Override
    public String toString() {
        return "ActividadFormacion{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", numero='" + numero + '\'' +
                ", titulo='" + titulo + '\'' +
                ", fech_realiz=" + fech_realiz +
//                ", encargados=" + encargados +
//                ", participantes=" + participantes +
                ", numVoluntarios=" + numVoluntarios +
                ", numParticipantes=" + numParticipantes +
                ", grupo_edad='" + grupo_edad + '\'' +
                ", duracion=" + duracion +
                ", objetivos='" + objetivos + '\'' +
                ", materiales='" + materiales + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", visible=" + visible +
                ", periodo=" + periodo +
                '}';
    }
}
