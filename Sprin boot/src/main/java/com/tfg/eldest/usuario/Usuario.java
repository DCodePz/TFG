package com.tfg.eldest.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static java.lang.Boolean.TRUE;

@Entity
@Table
public class Usuario {
    @Id
    private Long id;
    private String roles;
    private String password;
    private Boolean habilitado;

    public Usuario() {
    }

    public Usuario(Long id, String roles, String password) {
        this.id = id;
        this.roles = roles;
        this.password = password;
        this.habilitado = TRUE;
    }

//    -- Getters y Setters --

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String tipo) {
        this.roles = tipo;
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

//    -----------------------

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", roles='" + roles + '\'' +
                ", password='" + password + '\'' +
                ", habilitado=" + habilitado +
                '}';
    }
}