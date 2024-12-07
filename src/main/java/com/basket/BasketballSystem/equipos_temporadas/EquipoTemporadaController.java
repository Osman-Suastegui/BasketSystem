package com.basket.BasketballSystem.equipos_temporadas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/EquipoTemporada")
public class EquipoTemporadaController {



    EquipoTemporadaService equipoTemporadaService;

    @Autowired
    public EquipoTemporadaController(EquipoTemporadaService equipoTemporadaService) {
        this.equipoTemporadaService = equipoTemporadaService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @PostMapping("/crearEquipoTemporada")
    public ResponseEntity<Map<String, Object>> crearEquipoTemporada(@RequestBody EquipoTemporada equipoTemporada) {
       return equipoTemporadaService.crearEquipoTemporada(equipoTemporada);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @Transactional
    @DeleteMapping("/eliminarEquipoTemporada")
    public ResponseEntity<Map<String, Object>> eliminarEquipoTemporada(@RequestBody EquipoTemporada equipoTemporada) {
       return equipoTemporadaService.eliminarEquipoTemporada(equipoTemporada);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @GetMapping("/obtenerEquiposTemporada")
    public ResponseEntity<Map<String, Object>> obtenerEquiposTemporada(@RequestParam(name = "temporadaId",required = false) Long temporadaId) {
        return equipoTemporadaService.obtenerEquiposTemporada(temporadaId);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @GetMapping("/obtenerEquiposNoEnTemporada")
    public List<String> obtenerEquiposNoEnTemporada(@RequestParam(name = "temporadaId",required = false) Long temporadaId) {
        return equipoTemporadaService.obtenerEquiposNoEnTemporada(temporadaId);
    }





}
