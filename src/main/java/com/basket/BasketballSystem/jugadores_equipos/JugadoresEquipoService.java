package com.basket.BasketballSystem.jugadores_equipos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JugadoresEquipoService {

    JugadoresEquipoRepository jugadoresEquipoRepository;

    @Autowired
    public JugadoresEquipoService(JugadoresEquipoRepository jugadoresEquipoRepository) {
        this.jugadoresEquipoRepository = jugadoresEquipoRepository;
    }
}
