package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.partidos.Partido;
import com.basket.BasketballSystem.usuarios.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "jugadores_partidos")
public class JugadorPartido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clave;

    @ManyToOne
    @JoinColumn(name = "clave_partido")
    private Partido partido;

    @ManyToOne
    @JoinColumn(name = "jugador")
    private Usuario jugador;

    @Column(name = "equipo")
    private String equipo;

    @Column(name = "anotaciones")
    private Integer anotaciones;

    @Column(name = "tiros_de_3_puntos")
    private Integer tirosDe3Puntos;

    @Column(name = "tiros_de_2_puntos")
    private Integer tirosDe2Puntos;

    @Column(name = "tiros_libres")
    private Integer tirosLibres;

    @Column(name = "faltas")
    private Integer faltas;

    @Column(name = "asistencias")
    private Integer asistencias;

    @Column(name = "posicion")
    private String posicion;

    @Column(name="enBanca")
    private Boolean enBanca;


    @Transient
    private String descripcion; // Este campo no se persistirá en la base de datos

    // Getters y setters para todos los campos, incluido "descripcion"

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public JugadorPartido(Partido partido, Usuario jugador, String equipo, String posicion) {
        this.partido = partido;
        this.jugador = jugador;
        this.equipo = equipo;
        this.posicion = posicion;
    }

    public JugadorPartido() {
    }

    public Long getClave() {
        return clave;
    }

    public void setClave(Long clave) {
        this.clave = clave;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Usuario getJugador() {
        return jugador;
    }

    public void setJugador(Usuario jugador) {
        this.jugador = jugador;
    }

    public String getEquipo() {
        return equipo;
    }



    public Integer getAnotaciones() {
        return anotaciones;
    }

    public void setAnotaciones(Integer anotaciones) {
        this.anotaciones = anotaciones;
    }

    public Integer getTirosDe3Puntos() {
        return tirosDe3Puntos;
    }

    public void setTirosDe3Puntos(Integer tirosDe3Puntos) {
        this.tirosDe3Puntos = tirosDe3Puntos;
    }

    public Integer getTirosDe2Puntos() {
        return tirosDe2Puntos;
    }

    public void setTirosDe2Puntos(Integer tirosDe2Puntos) {
        this.tirosDe2Puntos = tirosDe2Puntos;
    }

    public Integer getTirosLibres() {
        return tirosLibres;
    }

    public void setTirosLibres(Integer tirosLibres) {
        this.tirosLibres = tirosLibres;
    }

    public Integer getFaltas() {
        return faltas;
    }

    public void setFaltas(Integer faltas) {
        this.faltas = faltas;
    }

    public Integer getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(Integer asistencias) {
        this.asistencias = asistencias;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return "JugadorPartido{" +
                "clave=" + clave +
                ", partido=" + partido +
                ", jugador=" + jugador +
                ", equipo='" + equipo + '\'' +
                ", anotaciones=" + anotaciones +
                ", tirosDe3Puntos=" + tirosDe3Puntos +
                ", tirosDe2Puntos=" + tirosDe2Puntos +
                ", tirosLibres=" + tirosLibres +
                ", faltas=" + faltas +
                ", asistencias=" + asistencias +
                ", posicion='" + posicion + '\'' +
                '}';
    }
}