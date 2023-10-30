package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;
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
    @Enumerated(EnumType.STRING)
    private Fase fase;

    @ManyToOne
    @JoinColumn(name = "arbitro")
    private Usuario arbitro;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP) // Indica que es un campo de fecha y hora
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant fechaInicio;

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

    public Fase getFase() {
        return fase;
    }

    public Usuario getArbitro() {
        return arbitro;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }
    public String getGanador() {
        if(ganador == null) return "";
        return ganador.getNombre();
    }
    @JsonIgnore
    public Equipo obtenerEquipoGanador(){
        return ganador;
    }



    public void setArbitro(Usuario arbitro) {
        this.arbitro = arbitro;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }


}
