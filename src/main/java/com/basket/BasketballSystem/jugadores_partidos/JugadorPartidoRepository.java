package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.partidos.Partido;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JugadorPartidoRepository extends JpaRepository<JugadorPartido, Long> {

    @Query("SELECT jp FROM JugadorPartido jp WHERE jp.partido.clavePartido = ?1 AND jp.jugador.usuario = ?2")
    JugadorPartido findByPartidoAndJugador(Long partidoId, String jugadorId);

    @Query("SELECT jp FROM JugadorPartido jp WHERE jp.jugador.usuario = ?1")
    List<JugadorPartido> findByJugador(String idJugador);

    //hazme usando el JPA repository que te de el jugador de un partido en base a su usuario, el partido y el equipo que sea del JPA
    @Query("SELECT jp FROM JugadorPartido jp WHERE jp.jugador.usuario = ?1 AND jp.partido.clavePartido = ?2")
    JugadorPartido findByJugadorAndPartido(String usuario, Long clavePartido);



    @Query("SELECT jp FROM JugadorPartido jp WHERE jp.equipo = ?1 AND jp.partido.clavePartido = ?2 AND (jp.enBanca = ?3 OR ?3 IS NULL)")
            List<JugadorPartido> findAllByEquipoAndPartidoAndNombre2(String nombreEquipo, Long clavePartido,Boolean enBancaFiltro);

    //necesioto obtener los jugadres de un partido que esten con enBanca= true
    @Query("SELECT jp FROM JugadorPartido jp WHERE jp.equipo = ?1 AND jp.partido.clavePartido = ?2 AND jp.enBanca = true")
    List<JugadorPartido> findAllByEquipoAndPartidoAndNombre(String nombreEquipo, Long clavePartido);

    //necesito un query que ponga un jugador en banca y otro query que lo quite de la banca
    @Modifying
    @Query("UPDATE JugadorPartido jp SET jp.enBanca = ?3 WHERE jp.partido.clavePartido = ?1 AND jp.jugador.usuario = ?2")
    void posicionarJugadorEnPartido(Long clavePartido, String usuario, Boolean enBanca);

    @Modifying
    @Query("UPDATE JugadorPartido jp SET jp.enBanca = ?1 WHERE jp.partido.clavePartido = ?2 AND jp.jugador.usuario = ?3 AND jp.equipo = ?4")
    void setEnBanca(Boolean enBanca, Long clavePartido, String jugador, String equipo);


}
