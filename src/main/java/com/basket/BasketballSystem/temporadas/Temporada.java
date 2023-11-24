package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "temporadas")
public class Temporada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claveTemporada;

    //añadele el valor unique
    @Column(name = "nombre_temporada", unique = true)
    private String nombreTemporada;

    @ManyToOne
    @JoinColumn(name = "id_liga")
    private Liga liga;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_termino")
    private LocalDate fechaTermino;



    @Column(name = "cantidad_equipos")
    private Integer cantidadEquipos;

    @Column(name = "cantidad_playoffs")
    private Integer cantidadPlayoffs;

    @Column(name = "cantidad_enfrentamientos_regular")
    private Integer cantidadEnfrentamientosRegular;
    @ManyToMany
    @JoinTable(
            name = "temporadas_arbitro",
            joinColumns = @JoinColumn(name = "clave_temporada"),
            inverseJoinColumns = @JoinColumn(name = "arbitro")
    )
    private List<Usuario> arbitros;
    @Enumerated(EnumType.STRING)
    private Estado estado; // en curso, terminada, cancelada

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private Rama rama;
    // Getters y setters


    public Temporada(String nombreTemporada, Liga liga, LocalDate fechaInicio, LocalDate fechaTermino, Integer cantidadEquipos, List<Usuario> arbitros, Categoria categoria, Rama rama) {
        this.nombreTemporada = nombreTemporada;
        this.liga = liga;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.cantidadEquipos = cantidadEquipos;
        this.arbitros = arbitros;
        this.categoria = categoria;
        this.rama = rama;
    }

    public Temporada() {
    }

    public int getCantidadEnfrentamientosRegular() {
        return cantidadEnfrentamientosRegular;
    }

    public Long getClaveTemporada() {
        return claveTemporada;
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

    public Estado getEstado() {
        return estado;
    }

    public Integer getCantidadEquipos() {
        return cantidadEquipos;
    }

    public Integer getCantidadEliminados() {
        return cantidadPlayoffs;
    }

    public List<Usuario> getArbitros() {
        return arbitros;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setCantidadEliminados(Integer cantidadEliminados) {
        this.cantidadPlayoffs = cantidadEliminados;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Rama getRama() {
        return rama;
    }


    public void setLiga(Liga ligaId) {
        this.liga = ligaId;
    }

    public void setCantidadEnfrentamientosRegular(Integer cantidadEnfrentamientosRegular) {
        this.cantidadEnfrentamientosRegular = cantidadEnfrentamientosRegular;
    }

    public void setCantidadEquipos(Integer cantidadEquipos) {
        this.cantidadEquipos = cantidadEquipos;
    }

    public void setCantidadPlayoffs(Integer cantidadPlayoffs) {
        this.cantidadPlayoffs = cantidadPlayoffs;
    }

    public Integer getCantidadPlayoffs() {
        return cantidadPlayoffs;
    }
}

