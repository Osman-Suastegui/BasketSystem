package com.basket.BasketballSystem.partidos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Partido")
public class PartidoController {

    @Autowired
    PartidoService partidoService;

    @RequestMapping ("/obtenerPartidosArbitro")
    public List<Partido> obtenerPartidosArbitro(@RequestParam("idArbitro") String idArbitro){

        return partidoService.obtenerPartidosArbitro(idArbitro);
    }


}
