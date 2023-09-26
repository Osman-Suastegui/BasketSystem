package com.basket.BasketballSystem.jugadores_partidos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JugadorPartidoRepository extends JpaRepository<JugadorPartido, Long> {

    @Query("SELECT jp FROM JugadorPartido jp WHERE jp.partido.clavePartido = ?1 AND jp.jugador.usuario = ?2")
    JugadorPartido findByPartidoAndJugador(Long partidoId, String jugadorId);

    @Query("SELECT jp FROM JugadorPartido jp WHERE jp.jugador.usuario = ?1")
    List<JugadorPartido> findByJugador(String idJugador);
}
