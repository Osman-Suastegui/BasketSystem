package com.basket.BasketballSystem.equipos_temporadas;

import com.basket.BasketballSystem.equipos.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/EquipoTemporada")
public class EquipoTemporadaController {



    EquipoTemporadaService equipoTemporadaService;

    @Autowired
    public EquipoTemporadaController(EquipoTemporadaService equipoTemporadaService) {
        this.equipoTemporadaService = equipoTemporadaService;
    }

    @PostMapping("/crearEquipoTemporada")
    public ResponseEntity<String> crearEquipoTemporada(@RequestBody EquipoTemporada equipoTemporada) {
       return equipoTemporadaService.crearEquipoTemporada(equipoTemporada);
    }





}
