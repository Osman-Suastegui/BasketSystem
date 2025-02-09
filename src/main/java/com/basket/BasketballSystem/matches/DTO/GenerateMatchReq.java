package com.basket.BasketballSystem.matches.DTO;

import com.basket.BasketballSystem.shared.TournamentType;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class GenerateMatchReq {
    TournamentType tournamentType;
    Long tournamentId;
    List<Long> teamIds;
}
