package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.ligas.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long> {

    @Query("SELECT t.claveTemporada, t.nombreTemporada " +
            "FROM Temporada t " +
            "INNER JOIN t.liga l " +
            "WHERE l.id = :ligaId")
    List<Object[]> findTemporadasForLiga(@Param("ligaId") Long ligaId);
    List<Temporada> findByNombreTemporadaContaining(String nombre);

    @Query(value = "SELECT u.usuario FROM usuarios u WHERE u.rol = 'ARBITRO' AND u.usuario NOT IN (SELECT ta.arbitro FROM temporadas_arbitro ta WHERE ta.clave_temporada = :temporadaId)", nativeQuery = true)
    List<String> findArbitrosNotInTemporada(@Param("temporadaId") Long temporadaId);



}
