package com.basket.BasketballSystem.tournaments;

import com.basket.BasketballSystem.user_tournament.UserTournament;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournaments")
@ToString
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //añadele el valor unique
    @Column(name = "name", unique = true)
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Sport sport;

    @Column(name = "cantidad_equipos")
    private Integer cantidadEquipos;

    @Enumerated(EnumType.STRING)
    private Estado estado; // en curso, terminada, cancelada

    @JsonIgnore
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTournament> userTournaments;

    public Tournament(String name, LocalDate fechaInicio, LocalDate fechaTermino, Integer cantidadEquipos) {
        this.name = name;
        this.startDate = fechaInicio;
        this.endDate = fechaTermino;
        this.cantidadEquipos = cantidadEquipos;
        this.userTournaments = new ArrayList<>();
    }

    public Tournament() {
        this.userTournaments = new ArrayList<>();

    }

    public Long getId() {
        return id;
    }

    public void setUserTournaments(List<UserTournament> userTournaments) {
        this.userTournaments = userTournaments;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Estado getEstado() {
        return estado;
    }

    public Integer getCantidadEquipos() {
        return cantidadEquipos;
    }


    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setCantidadEquipos(Integer cantidadEquipos) {
        this.cantidadEquipos = cantidadEquipos;
    }

    public List<UserTournament> getUserTournaments() {
        return userTournaments;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }
}

