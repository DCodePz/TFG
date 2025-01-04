package com.tfg.eldest.backend.rol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.eldest.backend.permiso.Permiso;
import com.tfg.eldest.backend.usuario.Usuario;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private Boolean habilitado;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<Usuario> usuarios;

    @ManyToMany
    @JoinTable(
            name = "rol_permiso",
            joinColumns = @JoinColumn(
                    name = "rol_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "permiso_id",
                    referencedColumnName = "id"
            )
    )
    private Collection<Permiso> permisos;

    public Rol() {
    }

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    public Rol(String nombre, Collection<Permiso> permisos) {
        this.nombre = nombre;
        this.permisos = permisos;
    }

    public Rol(String nombre, Boolean habilitado, Collection<Permiso> permisos) {
        this.nombre = nombre;
        this.habilitado = habilitado;
        this.permisos = permisos;
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

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Collection<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Collection<Permiso> permisos) {
        this.permisos = permisos;
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
        result.append("], permisos=[");

        if (permisos != null) {
            int i = 0;
            for (Permiso permiso : permisos) {
                result.append(permiso.getId()).append("-").append(permiso.getNombre());

                // Si no es el último elemento, añadir la coma
                if (++i < permisos.size()) {
                    result.append(", ");
                }
            }
        }
        result.append("]}");
        return result.toString();
    }
}