package com.tfg.eldest.backend.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.eldest.backend.actividadformacion.ActividadFormacion;
import com.tfg.eldest.backend.rol.Rol;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table
public class Usuario {
    @Id
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private Boolean habilitado;
    private String tipo;

    @ManyToMany(mappedBy = "encargados")
    @JsonIgnore
    private Collection<ActividadFormacion> actividadesFormacionesPreparadas;

    @ManyToMany(mappedBy = "participantes")
    @JsonIgnore
    private Collection<ActividadFormacion> actividadesFormacionesParticipadas;

    @ManyToMany
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(
                    name = "usuario_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "rol_id",
                    referencedColumnName = "id"
            )
    )
    private Collection<Rol> roles;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String apellidos, String email, String password, Boolean habilitado, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.habilitado = habilitado;
        this.tipo = tipo;
    }

    public Usuario(Long id, String nombre, String apellidos, String email, String password, String tipo, Collection<Rol> roles) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.tipo = tipo;
        this.roles = roles;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Collection<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Rol> roles) {
        this.roles = roles;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //    -----------------------

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", habilitado=" + habilitado +
                ", roles=[");

        if (roles != null) {
            int i = 0;
            for (Rol rol : roles) {
                result.append(rol.getId());

                if (++i < roles.size()) {
                    result.append(",");
                }
            }
        }
        result.append("]}");

        return result.toString();
    }
}