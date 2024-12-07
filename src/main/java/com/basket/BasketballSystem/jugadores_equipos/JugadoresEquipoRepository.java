package com.basket.BasketballSystem.jugadores_equipos;

import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadoresEquipoRepository extends JpaRepository<JugadoresEquipo, String> {

    @Modifying
    @Query("DELETE FROM JugadoresEquipo e WHERE e.team.nombre = :param1 AND e.jugador.usuario = :param2")
    void deleteByJugadorAndTeam(@Param("param1") String param1, @Param("param2") String param2);


    List<JugadoresEquipo> findAllByJugador(Usuario jugador);

//que sea por el nombre del equipo
    List<JugadoresEquipo> findAllByTeam(Team team);


    JugadoresEquipo findByJugadorAndTeam_Nombre(Usuario jugador, String nombreEquipo);

    @Query("SELECT u FROM Usuario u " +
            "WHERE u.rol = 'jugador' " +
            "AND u.usuario NOT IN (" +
            "    SELECT je.jugador.usuario FROM JugadoresEquipo je WHERE je.team.nombre = :equipoNombre" +
            ")")
    default List<Usuario> findJugadoresNotInTeamWithAgeAndGenderCondition(
            // Asegúrate de utilizar el tipo enumerado correcto
            @Param("equipoNombre") String equipoNombre
    ) {
        return null;
    }


    @Query(value= "SELECT je.jugador FROM jugadores_equipos je " +
            "WHERE je.nombre_equipo = ?1 " +
            "AND je.jugador NOT IN " +
            "(SELECT jp.jugador FROM jugadores_partidos jp " +
            "WHERE jp.clave_partido = ?2 AND (jp.team = ?1 OR jp.team = ?3))", nativeQuery = true)
    List<String> findJugadoresNoEnPartidos(String nombreEquipo, Long clavePartido, String otroEquipo);

}
