package com.basket.BasketballSystem.tournaments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    @Query("SELECT t.id, t.name " +
            "FROM Tournament t " +
            "INNER JOIN t.liga l " +
            "WHERE l.id = :ligaId")
    List<Object[]> findTournamentsForLiga(@Param("ligaId") Long ligaId);
    List<Tournament> findByNameContaining(String name);

    @Query(value = "SELECT u.usuario FROM usuarios u WHERE u.rol = 'ARBITRO' AND u.usuario NOT IN (SELECT ta.arbitro FROM temporadas_arbitro ta WHERE ta.clave_temporada = :temporadaId)", nativeQuery = true)
    List<String> findArbitrosNotInTemporada(@Param("temporadaId") Long temporadaId);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM temporadas_arbitro WHERE clave_temporada = :temporadaId AND arbitro = :arbitroId", nativeQuery = true)
    void deleteArbitroFromTemporada(@Param("temporadaId") Long temporadaId, @Param("arbitroId") String arbitroId);

    //recibe el id de la temporada y cambia su estado dependiendo de que mandaron
    @Modifying
    @Transactional
    @Query(value = "UPDATE temporadas SET estado = :estado WHERE id = :temporadaId", nativeQuery = true)
    void updateTemporadaEstado(@Param("temporadaId") Long temporadaId, @Param("estado") String estado);


}
