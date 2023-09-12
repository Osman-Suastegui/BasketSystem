package com.basket.BasketballSystem.equipos;

import com.basket.BasketballSystem.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "equipos")
public class Equipo {
    @Id
    private String nombre;
    @ManyToOne
    @JoinColumn(name="admin_equipo")
    private Usuario admin_equipo;


    public String getNombre() {
        return nombre;
    }

    public String getNombre_Admin_equipo() {
        return admin_equipo.getNombre();
    }
    public String getUsuario_Admin_equipo() {
        return admin_equipo.getUsuario();
    }
    @JsonIgnore
    public Equipo getEquipo(){
        return this;
    }



}
