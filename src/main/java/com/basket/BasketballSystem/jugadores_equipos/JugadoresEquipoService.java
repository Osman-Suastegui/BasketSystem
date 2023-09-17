package com.basket.BasketballSystem.jugadores_equipos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JugadoresEquipoService {

    @Autowired
    JugadoresEquipoRepository jugadoresEquipoRepository;

    @Autowired
    public JugadoresEquipoService(JugadoresEquipoRepository jugadoresEquipoRepository) {
        this.jugadoresEquipoRepository = jugadoresEquipoRepository;
    }

    public ResponseEntity<String> crearJugadoresEquipo(JugadoresEquipo jugadoresEquipo) {
        if (jugadoresEquipo == null) {
            return ResponseEntity.badRequest().body("El objeto JugadoresEquipo no puede ser nulo");
        }

        if (jugadoresEquipo.getEquipo() == null) {
            return ResponseEntity.badRequest().body("El equipo no puede ser nulo");
        }

        if (jugadoresEquipo.getUsuario() == null) {
            return ResponseEntity.badRequest().body("El usuario no puede ser nulo");
        }

        if (jugadoresEquipo.getPosicion() == null || jugadoresEquipo.getPosicion().isEmpty()) {
            return ResponseEntity.badRequest().body("La posición no puede ser nula o vacía");
        }

        // Aquí, no necesitas buscar el usuario en la base de datos nuevamente,
        // ya que ya lo tienes en el objeto JugadoresEquipo.
        // Puedes guardar directamente el objeto JugadoresEquipo.

        jugadoresEquipoRepository.save(jugadoresEquipo);

        return ResponseEntity.ok("Jugador asignado correctamente");
    }

    }

