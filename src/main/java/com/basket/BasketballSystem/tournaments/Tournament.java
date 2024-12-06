package com.basket.BasketballSystem.tournaments;

import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.user_tournament.UserTournament;
import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //añadele el valor unique
    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_liga")
    private Liga liga;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Sport sport;

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

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTournament> userTournaments;

    public Tournament(String nombreTemporada, Liga liga, LocalDate fechaInicio, LocalDate fechaTermino, Integer cantidadEquipos, List<Usuario> arbitros) {
        this.name = nombreTemporada;
        this.liga = liga;
        this.startDate = fechaInicio;
        this.endDate = fechaTermino;
        this.cantidadEquipos = cantidadEquipos;
        this.arbitros = arbitros;
    }

    public Tournament() {
    }

    public int getCantidadEnfrentamientosRegular() {
        return cantidadEnfrentamientosRegular;
    }

    public Long getClaveTemporada() {
        return id;
    }

    public String getNombreTemporada() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Estado getEstado() {
        return estado;
    }

    public Integer getCantidadEquipos() {
        return cantidadEquipos;
    }

    public List<Usuario> getArbitros() {
        return arbitros;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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

