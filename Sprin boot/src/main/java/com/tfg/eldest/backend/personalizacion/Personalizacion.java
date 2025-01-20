package com.tfg.eldest.backend.personalizacion;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table
public class Personalizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String color;
    private String foto;

    public Personalizacion() {
    }

    public Personalizacion(String nombre, String color, String foto) {
        this.nombre = nombre;
        this.color = color;
        this.foto = foto;
    }

    public Personalizacion(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    //    -----------------------

    @Override
    public String toString() {
        return "Personalizacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", color='" + color + '\'' +
                ", foto=" + foto +
                '}';
    }
}
