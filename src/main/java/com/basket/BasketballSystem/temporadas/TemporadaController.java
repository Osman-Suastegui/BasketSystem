package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.ligas.LigaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Temporadas")
public class TemporadaController {



    @Autowired
    TemporadaService TemporadaService;

    @PostMapping("/crearTemporada")
    public ResponseEntity<String> crearTemporada(@RequestBody Temporada temporada) {


        return TemporadaService.crearTemporada(temporada);
    }
    @PutMapping("/asignarLiga")
    public ResponseEntity<String> asignarLiga(@RequestBody Map<String, Object> requestMap) {
        Long ligaId = Long.parseLong(requestMap.get("ligaId").toString());
        Long temporadaId = Long.parseLong(requestMap.get("temporadaId").toString());

        return TemporadaService.asignarLiga(temporadaId, ligaId);

    }


}
