package com.basket.BasketballSystem.jugadores_equipos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.persistence.*;

@Entity()
@Table(name = "jugadores_equipos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"nombre_equipo"}))

public class JugadoresEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "nombre_equipo")
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "jugador")

    private Usuario usuario;

    private String posicion;

    public Equipo getEquipo() {
        return equipo;
    }
}
