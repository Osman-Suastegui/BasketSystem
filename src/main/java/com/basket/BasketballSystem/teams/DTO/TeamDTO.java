package com.basket.BasketballSystem.teams.DTO;

import lombok.Data;

@Data
public class TeamDTO {
    Long id;
    String name;
    Long tournamentId;
    Long createdById; // this property means the user who create the team
}
