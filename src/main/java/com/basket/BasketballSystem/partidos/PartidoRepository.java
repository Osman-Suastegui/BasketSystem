package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.equipos.Equipo;
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

    List<Partido> findAllByEquipo1InOrEquipo2In(List<Equipo> equipos1,List<Equipo> equipos2);

    @Query("SELECT p FROM Partido p WHERE p.equipo1.nombre = :nombreEquipo OR p.equipo2.nombre = :nombreEquipo")
    List<Partido> findByEquipo1NombreOrEquipo2Nombre(String nombreEquipo);

    List<Partido> findAllByTournament(Tournament tournament);
//        contar los partidos de una temporada

    @Query("SELECT p FROM Partido p WHERE p.tournament.id = :idTemporada")
    List<Partido> findAllByTournament(@Param("idTemporada") Long idTemporada);


    @Query("SELECT p FROM Partido p WHERE p.tournament.id = :idTemporada AND p.fase = :fase")
    List<Partido> findAllByTournamentAndFase(Long idTemporada, Fase fase);


    @Query("SELECT p.arbitro.usuario FROM Partido p WHERE p.clavePartido = :idPartido")
    String findArbitroByClavePartido(Long idPartido);


}
