package com.basket.BasketballSystem.equipos_temporadas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoTemporadaRepository extends JpaRepository<EquipoTemporada, Long> {

    void deleteByTemporadaClaveTemporadaAndEquipoNombre(Long claveTemporada, String nombreEquipo);

}
