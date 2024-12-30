package com.basket.BasketballSystem.tournaments.DTO;

import com.basket.BasketballSystem.tournaments.Tournament;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTournamentRequest {
    private Tournament tournament;
    private  Long userId;
}
