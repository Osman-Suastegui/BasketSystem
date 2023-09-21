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



}

