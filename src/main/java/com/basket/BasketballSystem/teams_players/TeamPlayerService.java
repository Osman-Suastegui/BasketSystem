package com.basket.BasketballSystem.teams_players;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerService {

    @Autowired
    TeamPlayerRepository teamPlayerRepository;

    @Autowired
    public TeamPlayerService(TeamPlayerRepository teamPlayerRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
    }



}
