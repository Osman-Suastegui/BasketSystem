package com.basket.BasketballSystem.equipos_temporadas;

import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.tournaments.Tournament;
import jakarta.persistence.*;

@Entity
@Table(name = "equipos_temporadas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "equipo"})
})
public class EquipoTemporada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clave_equiposTemporada")
    private Long clave;

    @ManyToOne
    @JoinColumn(name = "id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "equipo")
    private Team team;

    public EquipoTemporada(Tournament tournament, Team team) {
        this.tournament = tournament;
        this.team = team;
    }

    public EquipoTemporada() {
    }

    public Tournament getTemporada() {
        return tournament;
    }

    public Team getEquipo() {
        return team;
    }
}
