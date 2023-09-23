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

    @Column(name = "cantidad_eliminados")
    private Integer cantidadEliminados;
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
        return cantidadEliminados;
    }

    public List<Usuario> getArbitros() {
        return arbitros;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setCantidadEliminados(Integer cantidadEliminados) {
        this.cantidadEliminados = cantidadEliminados;
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
}

