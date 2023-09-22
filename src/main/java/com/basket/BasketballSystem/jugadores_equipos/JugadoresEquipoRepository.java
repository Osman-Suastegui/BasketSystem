package com.basket.BasketballSystem.jugadores_equipos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JugadoresEquipoRepository extends JpaRepository<JugadoresEquipo, String> {

    @Modifying
    @Query("DELETE FROM JugadoresEquipo e WHERE e.equipo.nombre = :param1 AND e.jugador.usuario = :param2")
    void deleteByJugadorAndEquipo(@Param("param1") String param1, @Param("param2") String param2);


    List<JugadoresEquipo> findAllByJugador(Usuario jugador);
}
