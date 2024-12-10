package com.basket.BasketballSystem.teams;

import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.jugadores_partidos.JugadorPartido;
import com.basket.BasketballSystem.tournaments.Categoria;
import com.basket.BasketballSystem.tournaments.Rama;
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
    private String nombre;

    @ManyToOne
    @JoinColumn(name="admin_equipo")
    private Usuario admin_equipo;

    private String logo;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<JugadorPartido> jugadoresPartidos;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<JugadoresEquipo> jugadores;

    public Team(String nombre, Usuario admin_equipo) {
        this.nombre = nombre;
        this.admin_equipo = admin_equipo;
    }

    public Team() {
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
    @JsonIgnore
    public void addJugador(JugadoresEquipo jugador){
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
