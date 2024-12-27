package com.tfg.eldest.periodo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.eldest.actividadformacion.ActividadFormacion;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.lang.Boolean.TRUE;

@Entity
@Table
public class Periodo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private LocalDate inicio;
    private LocalDate fin;
    private Boolean habilitado;

    @OneToMany(mappedBy = "periodo")
    @JsonBackReference  // Evita la serialización circular
    private Collection<ActividadFormacion> actividadesformaciones;

    public Periodo() {
    }

    public Periodo(String nombre, LocalDate inicio, LocalDate fin, Boolean habilitado) {
        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
        this.habilitado = habilitado;
    }

    //    -- Getters y Setters --

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Collection<ActividadFormacion> getActividadesformaciones() {
        return actividadesformaciones;
    }

    public void setActividadesformaciones(Collection<ActividadFormacion> actividadesformaciones) {
        this.actividadesformaciones = actividadesformaciones;
    }

    //    -----------------------

    @Override
    public String toString() {
        return "Periodo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", inicio=" + inicio +
                ", fin=" + fin +
                ", habilitado=" + habilitado +
//                ", actividadesformaciones=" + actividadesformaciones +
                '}';
    }
}
