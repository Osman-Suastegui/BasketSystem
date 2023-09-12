package com.basket.BasketballSystem.jugadores_equipos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadoresEquipoRepository extends JpaRepository<JugadoresEquipo, String> {
}
