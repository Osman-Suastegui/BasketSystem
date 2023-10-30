package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM temporadas_arbitro WHERE clave_temporada = :temporadaId AND arbitro = :arbitroId", nativeQuery = true)
    void deleteArbitroFromTemporada(@Param("temporadaId") Long temporadaId, @Param("arbitroId") String arbitroId);

    //recibe el id de la temporada y cambia su estado dependiendo de que mandaron
    @Modifying
    @Transactional
    @Query(value = "UPDATE temporadas SET estado = :estado WHERE clave_temporada = :temporadaId", nativeQuery = true)
    void updateTemporadaEstado(@Param("temporadaId") Long temporadaId, @Param("estado") String estado);


}
