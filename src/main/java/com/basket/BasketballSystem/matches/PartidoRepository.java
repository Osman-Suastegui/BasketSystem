package com.basket.BasketballSystem.matches;

import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.tournaments.Tournament;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Match,Long> {
    List<Match> findAllByArbitro(Usuario arbitro);

    List<Match> findAllByTeam1InOrTeam2In(List<Team> equipos1, List<Team> equipos2);

    @Query("SELECT p FROM Match p WHERE p.team1.name = :nombreEquipo OR p.team2.name = :nombreEquipo")
    List<Match> findByTeam1NombreOrTeam2Nombre(String nombreEquipo);

    List<Match> findAllByTournament(Tournament tournament);
//        contar los partidos de una temporada

    @Query("SELECT p FROM Match p WHERE p.tournament.id = :idTemporada")
    List<Match> findAllByTournament(@Param("idTemporada") Long idTemporada);


    @Query("SELECT p FROM Match p WHERE p.tournament.id = :idTemporada AND p.fase = :fase")
    List<Match> findAllByTournamentAndFase(Long idTemporada, Fase fase);


    @Query("SELECT p.arbitro.usuario FROM Match p WHERE p.id = :idPartido")
    String findArbitroById(Long idPartido);


}
