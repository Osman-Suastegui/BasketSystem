package com.basket.BasketballSystem.tournaments;

import com.basket.BasketballSystem.teams_tournaments.TeamTournament;
import com.basket.BasketballSystem.user_tournament.UserTournament;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tournaments")
@Data
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //añadele el valor unique
    @Column(name = "name", unique = true)
    private String name;

    private TournamentType tournamentType;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Sport sport;

    @Column(name = "cantidad_equipos")
    private Integer cantidadEquipos;

    @Enumerated(EnumType.STRING)
    private Estado estado; // en curso, terminada, cancelada

    private String description;
    private String rules;

    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<UserTournament> userTournaments = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamTournament> teamTournaments = new HashSet<>();

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now(); // Set the current date and time
    }

    public Tournament(String name, LocalDate fechaInicio, LocalDate fechaTermino, Integer cantidadEquipos) {
        this.name = name;
        this.startDate = fechaInicio;
        this.endDate = fechaTermino;
        this.cantidadEquipos = cantidadEquipos;
    }

    public Tournament() {
    }

}

