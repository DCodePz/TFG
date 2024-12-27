package com.tfg.eldest.rol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.eldest.usuario.Usuario;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<Usuario> usuarios;

    public Rol() {
    }

    public Rol(String nombre) {
        this.nombre = nombre;
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

    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    //    -----------------------

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder("Rol{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", usuarios=[");

        if (usuarios != null) {
            int i = 0;
            for (Usuario usuario : usuarios) {
                result.append(usuario.getId());

                // Si no es el último elemento, añadir la coma
                if (++i < usuarios.size()) {
                    result.append(", ");
                }
            }
        }
        result.append("]}");

        return result.toString();
    }
}