package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.temporadas.Temporada;
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

    List<Partido> findAllByTemporada(Temporada temporada);
//        contar los partidos de una temporada

    @Query("SELECT p FROM Partido p WHERE p.temporada.claveTemporada = :idTemporada")
    List<Partido> findAllByTemporada(@Param("idTemporada") Long idTemporada);


}
