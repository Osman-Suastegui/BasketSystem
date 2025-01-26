package com.basket.BasketballSystem.links;

import com.basket.BasketballSystem.teams_tournaments.TeamTournament;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;

@Data
@Entity
public class TeamTournamentLink {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_tournament_id")
    private TeamTournament teamTournament;

    private String token;
    private boolean active;
    private Instant createdAt;

    @PrePersist
    public void onCreate() {
        // Sets createdAt to "now" the moment the entity is persisted
        createdAt = Instant.now();
    }

}

