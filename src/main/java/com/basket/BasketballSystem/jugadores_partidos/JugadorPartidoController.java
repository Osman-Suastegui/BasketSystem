package com.basket.BasketballSystem.jugadores_partidos;

import com.basket.BasketballSystem.jugadores_partidos.DTO.actualizarJugadorPartidoDTO;
import com.basket.BasketballSystem.partidos.Partido;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/JugadorPartido")
public class JugadorPartidoController {

    @Autowired
    JugadorPartidoService jugadorPartidoService;




    @PostMapping("/agregarJugadorPartido")
    public ResponseEntity<String> agregarJugadorPartido(@RequestBody JugadorPartido jugadorPartido){
       return jugadorPartidoService.agregarJugadorPartido(jugadorPartido);
    }

    @PutMapping("/actualizarJugadorPartido")
    public ResponseEntity<String> actualizarJugadorPartido(@RequestBody actualizarJugadorPartidoDTO jugadorPartidoDTO){


        return jugadorPartidoService.actualizarJugadorPartido(jugadorPartidoDTO);
    }



}
