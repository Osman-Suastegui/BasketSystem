package com.basket.BasketballSystem.teams_tournaments;

import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.tournaments.Tournament;
import jakarta.persistence.*;

@Entity
@Table(name = "teams_tournaments")
public class TeamTournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public TeamTournament(Tournament tournament, Team team) {
        this.tournament = tournament;
        this.team = team;
    }

    public TeamTournament() {
    }

    public Tournament getTemporada() {
        return tournament;
    }

    public Team getEquipo() {
        return team;
    }
}
