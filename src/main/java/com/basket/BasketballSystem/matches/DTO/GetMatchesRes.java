package com.basket.BasketballSystem.matches.DTO;

import com.basket.BasketballSystem.teams.Team;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetMatchesRes {
    Long id;
    int round;
    TeamMatchesRes team1;
    TeamMatchesRes team2;
    Long next;
}
