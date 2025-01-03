package com.basket.BasketballSystem.user_tournament;
import com.basket.BasketballSystem.tournaments.Tournament;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_tournament")
public class UserTournament {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario user;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    @JsonIgnore
    private Tournament tournament;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // Example: "ORGANIZER", "REFEREE"

}
