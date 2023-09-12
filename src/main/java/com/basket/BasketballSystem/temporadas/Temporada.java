package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.ligas.Liga;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "temporadas")
public class Temporada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clave;

    @Column(name = "nombre_temporada")
    private String nombreTemporada;

    @ManyToOne
    @JoinColumn(name = "id_liga")
    private Liga liga;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_termino")
    private LocalDate fechaTermino;

    private String estado; // en curso, terminada, cancelada

    @Column(name = "cantidad_equipos")
    private Integer cantidadEquipos;

    @Column(name = "cantidad_eliminados")
    private Integer cantidadEliminados;

    private String categoria;

    private String rama;

    // Getters y setters


    public Integer getClave() {
        return clave;
    }

    public String getNombreTemporada() {
        return nombreTemporada;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaTermino() {
        return fechaTermino;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getCantidadEquipos() {
        return cantidadEquipos;
    }

    public Integer getCantidadEliminados() {
        return cantidadEliminados;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getRama() {
        return rama;
    }
}

