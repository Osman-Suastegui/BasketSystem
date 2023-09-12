package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.partidos.Partido;
import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "jugadores_partidos")
public class JugadorPartido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clave;

    @ManyToOne
    @JoinColumn(name = "clave_partido")
    private Partido partido;

    @ManyToOne
    @JoinColumn(name = "jugador")
    private Usuario jugador;

    @Column(name = "equipo")
    private String equipo;

    @Column(name = "anotaciones")
    private Integer anotaciones;

    @Column(name = "tiros_de_3_puntos")
    private Integer tirosDe3Puntos;

    @Column(name = "tiros_de_2_puntos")
    private Integer tirosDe2Puntos;

    @Column(name = "tiros_libres")
    private Integer tirosLibres;

    @Column(name = "faltas")
    private Integer faltas;

    @Column(name = "asistencias")
    private Integer asistencias;

    @Column(name = "posicion")
    private String posicion;

    // Getters y setters
}