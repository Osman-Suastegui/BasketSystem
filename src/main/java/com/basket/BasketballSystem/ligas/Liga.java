package com.basket.BasketballSystem.ligas;


import com.basket.BasketballSystem.temporadas.Temporada;
import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ligas")
public class Liga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre; // nombre de la liga

    @ManyToMany
    @JoinTable(
            name = "ligas_administradores",
            joinColumns = @JoinColumn(name = "liga_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario")
    )
    private List<Usuario> administradores; // Lista de administradores asociadas a la liga

    @OneToMany(mappedBy = "liga")
    private List<Temporada> temporadas; // Lista de temporadas asociadas a la liga

    public List<Temporada> getTemporadas() {
        return this.temporadas;
    }

    public List<Usuario> getAdministradores() {
        return this.administradores;
    }


    // Getters y setters
}