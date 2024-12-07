package com.basket.BasketballSystem.equipos_temporadas;

import com.basket.BasketballSystem.teams.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoTemporadaRepository extends JpaRepository<EquipoTemporada, Long> {

    void deleteByTournamentIdAndTeamNombre(Long claveTemporada, String nombreEquipo);


    @Query("SELECT e.team FROM EquipoTemporada e WHERE e.tournament.id = ?1")
    List<Team> findAllTeamsByTournament(Long idTemporada);

    @Query("SELECT e.team FROM EquipoTemporada e WHERE e.tournament.id = :temporadaId")
    List<Team> findTeamsByClaveTemporada(@Param("temporadaId") Long temporadaId);


    @Query(value = "SELECT e.nombre " +
            "FROM teams e " +
            "WHERE e.nombre NOT IN ( " +
            "  SELECT et.team " +
            "  FROM equipos_temporadas et " +
            "  WHERE et.id = :claveTemporada " +
            ") ", nativeQuery = true)
    List<String> findTeamsNotInTemporadaAndCategoryAndGender(
            @Param("claveTemporada") Long claveTemporada
    );




}
