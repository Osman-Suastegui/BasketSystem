package com.basket.BasketballSystem.partidos;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido,Long> {
    List<Partido> findAllByArbitro(Usuario arbitro);

    List<Partido> findAllByEquipo1InOrEquipo2In(List<Equipo> equipos1,List<Equipo> equipos2);
}
