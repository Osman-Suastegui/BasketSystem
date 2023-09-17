package com.basket.BasketballSystem.jugadores_equipos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jugadores-equipo")
public class JugadoresEquipoController {
    JugadoresEquipoService jugadoresEquipoService;


    @Autowired
    public JugadoresEquipoController(JugadoresEquipoService jugadoresEquipoService) {
        this.jugadoresEquipoService = jugadoresEquipoService;
    }

    @PostMapping("/crearJugadoresEquipo")
    public ResponseEntity<String> crearJugadoresEquipo(@RequestBody JugadoresEquipo jugadoresEquipo){
        return jugadoresEquipoService.crearJugadoresEquipo(jugadoresEquipo);
    }



}
