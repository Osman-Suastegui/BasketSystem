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
public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, String> {

    @Modifying
    @Query("DELETE FROM TeamPlayer e WHERE e.team.name = :param1 AND e.player.usuario = :param2")
    void deleteByPlayerAndTeam(@Param("param1") String param1, @Param("param2") String param2);


    List<TeamPlayer> findAllByPlayer(Usuario jugador);

//que sea por el nombre del equipo
    List<TeamPlayer> findAllByTeam(Team team);


    TeamPlayer findByPlayerAndTeam_Name(Usuario jugador, String nombreEquipo);

    @Query("SELECT u FROM Usuario u " +
            "WHERE u.rol = 'jugador' " +
            "AND u.usuario NOT IN (" +
            "    SELECT je.player.usuario FROM TeamPlayer je WHERE je.team.name = :equipoNombre" +
            ")")
    default List<Usuario> findPlayersNotInTeamWithAgeAndGenderCondition(
            // Asegúrate de utilizar el tipo enumerado correcto
            @Param("equipoNombre") String equipoNombre
    ) {
        return null;
    }

}
