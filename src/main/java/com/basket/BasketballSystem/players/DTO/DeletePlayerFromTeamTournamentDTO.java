package com.basket.BasketballSystem.players.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletePlayerFromTeamTournamentDTO {
    Long teamId;
    Long tournamentId;
    Long playerId;
}
