package com.basket.BasketballSystem.teams;

import com.basket.BasketballSystem.teams_players.TeamPlayer;
import com.basket.BasketballSystem.matches_player.MatchPlayer;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @ManyToOne
    @JoinColumn(name="admin_equipo")
    private Usuario admin_equipo;

    private String logo;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<MatchPlayer> jugadoresPartidos;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamPlayer> jugadores;

    public Team(String name, Usuario admin_equipo) {
        this.name = name;
        this.admin_equipo = admin_equipo;
    }

    public Team() {
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 2, max = 30) String name) {
        this.name = name;
    }

    public void setAdmin_equipo(Usuario admin_equipo) {
        this.admin_equipo = admin_equipo;
    }

    public String getNombre_Admin_equipo() {
        return admin_equipo.getName();
    }
    public String getUsuario_Admin_equipo() {
        return admin_equipo.getUsuario();
    }
    public List<TeamPlayer> getJugadores() {
        return jugadores;
    }
    @JsonIgnore
    public void addJugador(TeamPlayer jugador){
        jugadores.add(jugador);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


}
