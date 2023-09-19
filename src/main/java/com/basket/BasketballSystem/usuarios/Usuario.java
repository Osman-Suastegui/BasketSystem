package com.basket.BasketballSystem.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    private String usuario;

    @Column(unique = true)
    private String email;

    private String password;
    private String nombre;
    private LocalDate fechaNacimiento;

    private String apellido;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Usuario() {
    }

    public Usuario(String usuario, String email, String password, String nombre, LocalDate fechaNacimiento, String apellido, Genero genero, Rol rol) {
        this.usuario = usuario;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.apellido = apellido;
        this.genero = genero;
        this.rol = rol;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Genero getGenero() {
        return genero;
    }

    public Rol getRol() {
        return rol;
    }
    public Integer getEdad(){
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public String getPassword(){
        return password;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
