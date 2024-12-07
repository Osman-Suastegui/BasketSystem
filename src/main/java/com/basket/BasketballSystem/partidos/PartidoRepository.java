package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.teams.Team;
import com.basket.BasketballSystem.tournaments.Tournament;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido,Long> {
    List<Partido> findAllByArbitro(Usuario arbitro);

    List<Partido> findAllByTeam1InOrTeam2In(List<Team> equipos1, List<Team> equipos2);

    @Query("SELECT p FROM Partido p WHERE p.team1.nombre = :nombreEquipo OR p.team2.nombre = :nombreEquipo")
    List<Partido> findByTeam1NombreOrTeam2Nombre(String nombreEquipo);

    List<Partido> findAllByTournament(Tournament tournament);
//        contar los partidos de una temporada

    @Query("SELECT p FROM Partido p WHERE p.tournament.id = :idTemporada")
    List<Partido> findAllByTournament(@Param("idTemporada") Long idTemporada);


    @Query("SELECT p FROM Partido p WHERE p.tournament.id = :idTemporada AND p.fase = :fase")
    List<Partido> findAllByTournamentAndFase(Long idTemporada, Fase fase);


    @Query("SELECT p.arbitro.usuario FROM Partido p WHERE p.clavePartido = :idPartido")
    String findArbitroByClavePartido(Long idPartido);


}
