package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "partidos")
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clave_partido")
    private Long clavePartido;

    @ManyToOne
    @JoinColumn(name = "clave_temporada")
    private Temporada temporada;

    @ManyToOne
    @JoinColumn(name = "equipo1")
    private Equipo equipo1;

    @ManyToOne
    @JoinColumn(name = "equipo2")
    private Equipo equipo2;

    @Column(name = "fase")
    private String fase;

    @ManyToOne
    @JoinColumn(name = "arbitro")
    private Usuario arbitro;

    // Getters y setters
}
