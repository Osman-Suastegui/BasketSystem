package com.basket.BasketballSystem.tournaments;

import com.basket.BasketballSystem.user_tournament.UserTournament;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Sport sport;

    @Column(name = "cantidad_equipos")
    private Integer cantidadEquipos;

    @Enumerated(EnumType.STRING)
    private Estado estado; // en curso, terminada, cancelada

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
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

}

