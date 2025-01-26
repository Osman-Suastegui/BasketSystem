package com.basket.BasketballSystem.players;

import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "players")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = true) // One-to-one relationship with User
    private Usuario user;


    @Column(name = "name")
    private String name;

    // Other fields
}
