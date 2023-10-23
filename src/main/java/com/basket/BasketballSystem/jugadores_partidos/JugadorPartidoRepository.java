package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.partidos.Partido;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
