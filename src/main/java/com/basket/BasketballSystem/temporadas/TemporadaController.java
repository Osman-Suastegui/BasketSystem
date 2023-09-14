package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.ligas.LigaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Temporadas")
public class TemporadaController {



    @Autowired
    TemporadaService TemporadaService;

    @PostMapping("/crearTemporada")
    public ResponseEntity<String> crearTemporada(@RequestBody Temporada temporada) {


        return TemporadaService.crearTemporada(temporada);
    }


}
