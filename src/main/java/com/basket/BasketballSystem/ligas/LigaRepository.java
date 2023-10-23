package com.basket.BasketballSystem.ligas;

import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Long> {
    Optional<Liga> findByNombre(String nombre);

    List<Liga> findByNombreContaining(String nombre);

    //@Query("SELECT l.id as ligaId, l.nombre as nombreLiga, t.claveTemporada, t.nombreTemporada " +
          //  "FROM Liga l " +
         //   "INNER JOIN l.administradores a " +
       //     "INNER JOIN l.temporadas t " +
     //       "WHERE a.usuario = :username")
   // List<Object[]> findLigaInfoForUser(@Param("username") String username);

    @Query("SELECT l.id as ligaId, l.nombre as nombreLiga " +
            "FROM Liga l " +
            "INNER JOIN l.administradores a " +
            "WHERE a.usuario = :username")
    List<Object[]> findLigaInfoForUser(@Param("username") String username);


    @Query(value = "SELECT u.usuario FROM Usuarios u " +
            "WHERE u.rol = 'ADMIN_LIGA' " +
            "AND u.usuario NOT IN (" +
            "    SELECT la.usuario FROM ligas_administradores la " +
            "    WHERE la.liga_id = :ligaId)", nativeQuery = true)
    List<String> findAdminLigaNotInLiga(Long ligaId);

    @Query(value = "SELECT la.usuario FROM ligas_administradores la WHERE la.liga_id = :ligaId", nativeQuery = true)
    List<String> findUsuariosByLigaId(Long ligaId);


}
