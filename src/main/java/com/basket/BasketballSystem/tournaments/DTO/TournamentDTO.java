package com.basket.BasketballSystem.tournaments.DTO;
import com.basket.BasketballSystem.tournaments.Estado;
import com.basket.BasketballSystem.tournaments.Sport;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import java.util.List;

@Data
public class TournamentDTO {
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Sport sport;
    @Enumerated(EnumType.STRING)
    private Estado estado; // en curso, terminada, cancelada
    private List<UserDTO> users;
}
