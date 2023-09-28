package com.basket.BasketballSystem.equipos_temporadas;

import com.basket.BasketballSystem.equipos.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoTemporadaRepository extends JpaRepository<EquipoTemporada, Long> {

    void deleteByTemporadaClaveTemporadaAndEquipoNombre(Long claveTemporada, String nombreEquipo);


    @Query("SELECT e.equipo FROM EquipoTemporada e WHERE e.temporada.claveTemporada = ?1")
    List<Equipo> findAllEquiposByTemporada(Long idTemporada);

}
