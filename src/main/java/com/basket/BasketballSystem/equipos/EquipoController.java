package com.basket.BasketballSystem.equipos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Equipo")

public class EquipoController {
    EquipoService equipoService;

    @Autowired
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @RequestMapping("/obtenerEquipoAdminEquipo")
    public Equipo obtenerEquipoAdminEquipo(@RequestParam("idAdminEquipo") String idAdminEquipo) {
        return equipoService.obtenerEquipoAdminEquipo(idAdminEquipo);
    }



}
