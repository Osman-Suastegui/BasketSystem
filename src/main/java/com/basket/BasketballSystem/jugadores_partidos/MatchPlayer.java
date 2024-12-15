package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.matches.Match;
import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "matches_players")
public class MatchPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clave;

    @ManyToOne
    @JoinColumn(name = "clave_partido")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToOne
    @JoinColumn(name = "jugador")
    private Usuario jugador;

    @Column(name = "equipo")
    private String equipo;

    @Column(name = "anotaciones")
    private Integer anotaciones;

    @Column(name = "faltas")
    private Integer faltas;

    @Column(name = "asistencias")
    private Integer asistencias;

    @Column(name = "posicion")
    private String posicion;

    @Column(name="enBanca")
    private Boolean enBanca;


    @Transient
    private String descripcion; // Este campo no se persistirá en la base de datos

    // Getters y setters para todos los campos, incluido "descripcion"

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MatchPlayer(Match match, Usuario jugador, String equipo, String posicion) {
        this.match = match;
        this.jugador = jugador;
        this.equipo = equipo;
        this.posicion = posicion;
    }

    public MatchPlayer() {
    }

    public Long getClave() {
        return clave;
    }

    public void setClave(Long clave) {
        this.clave = clave;
    }

    public Match getPartido() {
        return match;
    }

    public void setPartido(Match match) {
        this.match = match;
    }

    public Usuario getJugador() {
        return jugador;
    }

    public void setJugador(Usuario jugador) {
        this.jugador = jugador;
    }

    public String getEquipo() {
        return equipo;
    }



    public Integer getAnotaciones() {
        return anotaciones;
    }

    public void setAnotaciones(Integer anotaciones) {
        this.anotaciones = anotaciones;
    }

    public Integer getFaltas() {
        return faltas;
    }

    public void setFaltas(Integer faltas) {
        this.faltas = faltas;
    }

    public Integer getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(Integer asistencias) {
        this.asistencias = asistencias;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }



    public void setEnBanca(Boolean enBanca) {
        this.enBanca = enBanca;
    }

    @Override
    public String toString() {
        return "JugadorPartido{" +
                "clave=" + clave +
                ", partido=" + match +
                ", jugador=" + jugador +
                ", equipo='" + equipo + '\'' +
                ", anotaciones=" + anotaciones +
                ", faltas=" + faltas +
                ", asistencias=" + asistencias +
                ", posicion='" + posicion + '\'' +
                '}';
    }

    public Boolean getEnBanca() {
        return enBanca;
    }
}