package com.basket.BasketballSystem.jugadores_equipos;

import com.basket.BasketballSystem.teams.Team;
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
    private Team team;

    @ManyToOne
    @JoinColumn(name = "jugador")
    private Usuario jugador;

    private String posicion;

    public JugadoresEquipo() {
    }

    public JugadoresEquipo(Long id, Team team, Usuario jugador, String posicion) {
        this.id = id;
        this.team = team;
        this.jugador = jugador;
        this.posicion = posicion;
    }

    public Usuario getJugador() {
        return jugador;
    }


    //lo voy a descomentar dia 04/11/23
    @JsonIgnore
    public String getNombreEquipo() {
        return team.getNombre();
    }


    public Long getId() {
        return id;
    }

    public String getPosicion() {
        return posicion;
    }

    //lo voy a descomentar dia 04/11/23
    @JsonIgnore
    public Team getEquipo() {
        return team;
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

    public void setEquipo(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "JugadoresEquipo{" +
                "equiponombre=" + team.getNombre() +
                ", jugador=" + jugador.getUsuario() +
                ", posicion='" + posicion + '\'' +
                '}';
    }
}
