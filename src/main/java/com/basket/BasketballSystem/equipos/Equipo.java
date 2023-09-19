package com.basket.BasketballSystem.equipos;

import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "equipos")
public class Equipo {
    @Id
    private String nombre;
    @ManyToOne
    @JoinColumn(name="admin_equipo")
    private Usuario admin_equipo;

    @OneToMany(mappedBy = "equipo")
    private List<JugadoresEquipo> jugadores;

    public Equipo(String nombre, Usuario admin_equipo) {
        this.nombre = nombre;
        this.admin_equipo = admin_equipo;
    }

    public Equipo() {
    }




    public String getNombre() {
        return nombre;
    }

    public String getNombre_Admin_equipo() {
        return admin_equipo.getNombre();
    }
    public String getUsuario_Admin_equipo() {
        return admin_equipo.getUsuario();
    }

    public Usuario getAdmin_equipo() {
        return admin_equipo;
    }

    public List<JugadoresEquipo> getJugadores() {
        return jugadores;
    }





}
