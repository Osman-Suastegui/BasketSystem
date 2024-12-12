package com.basket.BasketballSystem.matches;

import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.tournaments.Tournament;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team1_id")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id")
    private Team team2;

    @Column(name = "fase")
    @Enumerated(EnumType.STRING)
    private Fase fase;

    @ManyToOne
    @JoinColumn(name = "referee_id")
    private Usuario arbitro;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP) // Indica que es un campo de fecha y hora
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant fechaInicio;


    @Column(name = "ganador")
    private String ganador;

    // Getters y setters

    public Long getClavePartido() {
        return id;
    }

    public Tournament getTemporada() {
        return tournament;
    }

    public Team getEquipo1() {
        return team1;
    }

    public Team getEquipo2() {
        return team2;
    }

    public Fase getFase() {
        return fase;
    }

    public Usuario getArbitro() {
        return arbitro;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }
    public String getGanador() {
        if(ganador == null) return "";
        return ganador;
    }
    @JsonIgnore
    public String obtenerEquipoGanador(){
        return ganador;
    }


    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public void setArbitro(Usuario arbitro) {
        this.arbitro = arbitro;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setTemporada(Tournament tournament) {
        this.tournament = tournament;
    }

    public void setEquipo1(Team team1) {
        this.team1 = team1;
    }

    public void setEquipo2(Team team2) {
        this.team2 = team2;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

}
