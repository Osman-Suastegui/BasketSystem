package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.matches.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Long> {

    @Query("SELECT jp FROM MatchPlayer jp WHERE jp.match.id = ?1 AND jp.jugador.usuario = ?2")
    MatchPlayer findByMatchAndJugador(Long partidoId, String jugadorId);

    @Query("SELECT jp FROM MatchPlayer jp WHERE jp.jugador.usuario = ?1")
    List<MatchPlayer> findByJugador(String idJugador);

    //hazme usando el JPA repository que te de el jugador de un partido en base a su usuario, el partido y el equipo que sea del JPA
    @Query("SELECT jp FROM MatchPlayer jp WHERE jp.jugador.usuario = ?1 AND jp.match.id = ?2")
    MatchPlayer findByJugadorAndMatch(String usuario, Long clavePartido);


    @Query("SELECT jp FROM MatchPlayer jp WHERE jp.equipo = ?1 AND jp.match.id = ?2 AND (jp.enBanca = ?3 OR ?3 IS NULL)")
            List<MatchPlayer> findAllByEquipoAndMatchAndNombre2(String nombreEquipo, Long clavePartido, Boolean enBancaFiltro);

    //necesioto obtener los jugadres de un partido que esten con enBanca= true
    @Query("SELECT jp FROM MatchPlayer jp WHERE jp.equipo = ?1 AND jp.match.id = ?2 AND jp.enBanca = true")
    List<MatchPlayer> findAllByEquipoAndMatchAndNombre(String nombreEquipo, Long clavePartido);

    //necesito un query que ponga un jugador en banca y otro query que lo quite de la banca
    @Modifying
    @Query("UPDATE MatchPlayer jp SET jp.enBanca = ?3 WHERE jp.match.id = ?1 AND jp.jugador.usuario = ?2")
    void posicionarJugadorEnMatch(Long clavePartido, String usuario, Boolean enBanca);

    @Modifying
    @Query("UPDATE MatchPlayer jp SET jp.enBanca = ?1 WHERE jp.match.id = ?2 AND jp.jugador.usuario = ?3 AND jp.equipo = ?4")
    void setEnBanca(Boolean enBanca, Long clavePartido, String jugador, String equipo);


    List<MatchPlayer> findAllByMatchIn(List<Match> matches);

    @Query("SELECT jp FROM MatchPlayer jp WHERE jp.match.id = ?1")
    List<MatchPlayer> findAllByMatch(Long clavePartido);
}
