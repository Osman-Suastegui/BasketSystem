package com.basket.BasketballSystem.jugadores_equipos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.usuarios.Usuario;

import jakarta.persistence.*;

@Entity()
@Table(name = "jugadores_equipos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"nombre_equipo", "jugador"}))

public class JugadoresEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "nombre_equipo")
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "jugador")
    private Usuario usuario;

    private String posicion;

    public JugadoresEquipo() {
    }

    public JugadoresEquipo(Long id, Equipo equipo, Usuario usuario, String posicion) {
        this.id = id;
        this.equipo = equipo;
        this.usuario = usuario;
        this.posicion = posicion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getNombreUsuario() {
        return usuario.getNombre();
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public String getNombreEquipo() {
        return equipo.getNombre();
    }


    public Long getId() {
        return id;
    }



    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }



    public String getPosicion() {
        return posicion;
    }







}
