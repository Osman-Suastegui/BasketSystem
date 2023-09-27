package com.basket.BasketballSystem.jugadores_partidos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/JugadorPartido")
public class JugadorPartidoController {

    @Autowired
    JugadorPartidoService jugadorPartidoService;




    @PostMapping("/agregarJugadorPartido")
    public ResponseEntity<String> agregarJugadorPartido(@RequestBody JugadorPartido jugadorPartido){
       return jugadorPartidoService.agregarJugadorPartido(jugadorPartido);
    }

}
