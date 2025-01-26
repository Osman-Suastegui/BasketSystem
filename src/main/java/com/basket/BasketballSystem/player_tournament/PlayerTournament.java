package com.basket.BasketballSystem.player_tournament;
import com.basket.BasketballSystem.teams_tournaments.TeamTournament;
import com.basket.BasketballSystem.players.Player;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "players_tournaments")
public class PlayerTournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_tournament_id")
    private TeamTournament teamTournament;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public PlayerTournament() {}

    public PlayerTournament(TeamTournament teamTournament, Player player) {
        this.teamTournament = teamTournament;
        this.player = player;
    }

}
