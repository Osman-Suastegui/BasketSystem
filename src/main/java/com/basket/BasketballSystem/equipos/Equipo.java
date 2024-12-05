package com.basket.BasketballSystem.equipos;

import com.basket.BasketballSystem.jugadores_equipos.JugadoresEquipo;
import com.basket.BasketballSystem.tournaments.Categoria;
import com.basket.BasketballSystem.tournaments.Rama;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "equipos")
public class Equipo {
    @Id
    @NotNull
    @Size(min = 3, max = 30)
    private String nombre;
    @ManyToOne
    @JoinColumn(name="admin_equipo")
    private Usuario admin_equipo;
    @Column(name = "rama")
    @Enumerated(EnumType.STRING)
    private Rama rama;

    @Column(name = "categoria")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
    private List<JugadoresEquipo> jugadores;

    public Equipo(String nombre, Usuario admin_equipo, Rama rama, Categoria categoria) {
        this.nombre = nombre;
        this.admin_equipo = admin_equipo;
        this.rama = rama;
        this.categoria = categoria;
    }

    public Equipo() {
    }




    public String getNombre() {
        return nombre;
    }

    public String getNombre_Admin_equipo() {
        return admin_equipo.getNombre();
    }
    public String getUsuario_Admin_equipo() {
        return admin_equipo.getUsuario();
    }

    public Usuario getAdmin_equipo() {
        return admin_equipo;
    }

    public List<JugadoresEquipo> getJugadores() {
        return jugadores;
    }
    @JsonIgnore
    public void addJugador(JugadoresEquipo jugador){
        jugadores.add(jugador);
    }



    public void setRama(Rama rama) {
        this.rama = rama;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Rama getRama() {
        return rama;
    }

    public Categoria getCategoria() {
        return categoria;
    }




}
