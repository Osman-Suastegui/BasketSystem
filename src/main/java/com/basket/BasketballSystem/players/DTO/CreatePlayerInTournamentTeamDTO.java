package com.basket.BasketballSystem.players.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class CreatePlayerInTournamentTeamDTO {
    private Long teamId;
    private Long tournamentId;
    private String playerName;
}
