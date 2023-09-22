package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

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

    @Column(name = "fecha_inicio")
    private Timestamp fechaInicio;

    @ManyToOne
    @JoinColumn(name = "resultado")
    private Equipo ganador;



    // Getters y setters

    public Long getClavePartido() {
        return clavePartido;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public String getFase() {
        return fase;
    }

    public Usuario getArbitro() {
        return arbitro;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }
    public String getGanador() {
        if(ganador == null) return "";
        return ganador.getNombre();
    }
}
