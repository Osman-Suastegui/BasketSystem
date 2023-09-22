package com.basket.BasketballSystem.equipos_temporadas;

import com.basket.BasketballSystem.equipos.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    @Transactional
    @DeleteMapping("/eliminarEquipoTemporada")
    public ResponseEntity<String> eliminarEquipoTemporada(@RequestBody EquipoTemporada equipoTemporada) {
       return equipoTemporadaService.eliminarEquipoTemporada(equipoTemporada);
    }





}
