package com.basket.BasketballSystem.teams_players;

import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.usuarios.Usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity()
@Table(name = "team_players")
public class TeamPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Usuario player;

    private String position;

    public TeamPlayer() {
    }

    public TeamPlayer(Long id, Team team, Usuario player, String position) {
        this.id = id;
        this.team = team;
        this.player = player;
        this.position = position;
    }

    public Usuario getPlayer() {
        return player;
    }


    //lo voy a descomentar dia 04/11/23
    @JsonIgnore
    public String getNombreEquipo() {
        return team.getName();
    }


    public Long getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    //lo voy a descomentar dia 04/11/23
    @JsonIgnore
    public Team getEquipo() {
        return team;
    }


    public void setPlayer(Usuario player) {
        this.player = player;
    }

    public void setPosition(String position) {
        this.position = position;
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
                "equiponombre=" + team.getName() +
                ", jugador=" + player.getUsuario() +
                ", posicion='" + position + '\'' +
                '}';
    }
}
