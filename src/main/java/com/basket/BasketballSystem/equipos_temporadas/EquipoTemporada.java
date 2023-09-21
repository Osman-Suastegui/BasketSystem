package com.basket.BasketballSystem.equipos_temporadas;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.temporadas.Temporada;
import jakarta.persistence.*;

@Entity
@Table(name = "equipos_temporadas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"clave_temporada", "equipo"})
})
public class EquipoTemporada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clave_equiposTemporada")
    private Long clave;

    @ManyToOne
    @JoinColumn(name = "clave_temporada")
    private Temporada temporada;

    @ManyToOne
    @JoinColumn(name = "equipo")
    private Equipo equipo;

    public EquipoTemporada(Temporada temporada, Equipo equipo) {
        this.temporada = temporada;
        this.equipo = equipo;
    }

    public EquipoTemporada() {
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public Equipo getEquipo() {
        return equipo;
    }
}
