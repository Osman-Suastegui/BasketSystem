package com.basket.BasketballSystem.jugadores_equipos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.jugadores_partidos.JugadorPartido;
import com.basket.BasketballSystem.temporadas.Rama;
import com.basket.BasketballSystem.usuarios.Genero;
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


    JugadoresEquipo findByJugadorAndEquipo_Nombre(Usuario jugador, String nombreEquipo);

    @Query("SELECT u FROM Usuario u " +
            "WHERE u.rol = 'jugador' " +
            "AND u.genero = :genero " +
            "AND FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', u.fechaNacimiento) BETWEEN :edadMin AND :edadMax " +
            "AND u.usuario NOT IN (" +
            "    SELECT je.jugador.usuario FROM JugadoresEquipo je WHERE je.equipo.nombre = :equipoNombre" +
            ")")
    List<Usuario> findJugadoresNotInEquipoWithAgeAndGenderCondition(
            @Param("edadMin") int edadMin,
            @Param("edadMax") int edadMax,
            @Param("genero") Genero genero,  // Asegúrate de utilizar el tipo enumerado correcto
            @Param("equipoNombre") String equipoNombre
    );









    @Query(value= "SELECT je.jugador FROM jugadores_equipos je " +
            "WHERE je.nombre_equipo = ?1 " +
            "AND je.jugador NOT IN " +
            "(SELECT jp.jugador FROM jugadores_partidos jp " +
            "WHERE jp.clave_partido = ?2 AND jp.equipo = ?1)", nativeQuery = true)
    List<String> findJugadoresNoEnPartidos(String nombreEquipo, Long clavePartido);




}
