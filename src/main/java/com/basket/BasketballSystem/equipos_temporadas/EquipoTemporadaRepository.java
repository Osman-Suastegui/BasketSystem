package com.basket.BasketballSystem.equipos_temporadas;

import com.basket.BasketballSystem.equipos.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoTemporadaRepository extends JpaRepository<EquipoTemporada, Long> {

    void deleteByTournamentIdAndEquipoNombre(Long claveTemporada, String nombreEquipo);


    @Query("SELECT e.equipo FROM EquipoTemporada e WHERE e.tournament.id = ?1")
    List<Equipo> findAllEquiposByTemporada(Long idTemporada);

    @Query("SELECT e.equipo FROM EquipoTemporada e WHERE e.tournament.id = :temporadaId")
    List<Equipo> findEquiposByClaveTemporada(@Param("temporadaId") Long temporadaId);


    @Query(value = "SELECT e.nombre " +
            "FROM equipos e " +
            "WHERE e.nombre NOT IN ( " +
            "  SELECT et.equipo " +
            "  FROM equipos_temporadas et " +
            "  WHERE et.id = :claveTemporada " +
            ") ", nativeQuery = true)
    List<String> findEquiposNotInTemporadaAndCategoryAndGender(
            @Param("claveTemporada") Long claveTemporada
    );




}
