package com.basket.BasketballSystem.equipos_temporadas;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.tournaments.Tournament;
import jakarta.persistence.*;

@Entity
@Table(name = "equipos_temporadas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "equipo"})
})
public class EquipoTemporada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clave_equiposTemporada")
    private Long clave;

    @ManyToOne
    @JoinColumn(name = "id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "equipo")
    private Equipo equipo;

    public EquipoTemporada(Tournament tournament, Equipo equipo) {
        this.tournament = tournament;
        this.equipo = equipo;
    }

    public EquipoTemporada() {
    }

    public Tournament getTemporada() {
        return tournament;
    }

    public Equipo getEquipo() {
        return equipo;
    }
}
