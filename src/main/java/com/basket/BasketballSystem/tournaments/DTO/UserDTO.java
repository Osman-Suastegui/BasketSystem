package com.basket.BasketballSystem.tournaments.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String role; // From UserTournament
    private String name;
    private String lastName;
}