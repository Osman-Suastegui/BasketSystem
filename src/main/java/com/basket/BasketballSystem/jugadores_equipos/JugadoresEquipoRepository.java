package com.basket.BasketballSystem.jugadores_equipos;

import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JugadoresEquipoRepository extends JpaRepository<JugadoresEquipo, String> {
}
