package com.tfg.eldest.periodo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

import static java.lang.Boolean.TRUE;

@Entity
@Table
public class Periodo {
    @Id
    private long id;
    private String nombre;
    private LocalDate inicio;
    private LocalDate fin;
    private Boolean habilitado;

    public Periodo() {
    }

    public Periodo(long id, String nombre, LocalDate inicio, LocalDate fin) {
        this.id = id;
        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
        this.habilitado = TRUE;
    }

//    -- Getters y Setters --

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    //    -----------------------

    @Override
    public String toString() {
        return "Periodo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", inicio=" + inicio +
                ", fin=" + fin +
                ", habilitado=" + habilitado +
                '}';
    }
}
