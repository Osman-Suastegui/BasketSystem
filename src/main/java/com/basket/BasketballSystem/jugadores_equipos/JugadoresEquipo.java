package com.basket.BasketballSystem.jugadores_equipos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.usuarios.Usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Usuario jugador;

    private String posicion;

    public JugadoresEquipo() {
    }

    public JugadoresEquipo(Long id, Equipo equipo, Usuario jugador, String posicion) {
        this.id = id;
        this.equipo = equipo;
        this.jugador = jugador;
        this.posicion = posicion;
    }

    public Usuario getJugador() {
        return jugador;
    }



    @JsonIgnore
    public String getNombreEquipo() {
        return equipo.getNombre();
    }


    public Long getId() {
        return id;
    }

    public String getPosicion() {
        return posicion;
    }


    public void setJugador(Usuario jugador) {
        this.jugador = jugador;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return "JugadoresEquipo{" +
                "equiponombre=" + equipo.getNombre() +
                ", jugador=" + jugador.getUsuario() +
                ", posicion='" + posicion + '\'' +
                '}';
    }
}
