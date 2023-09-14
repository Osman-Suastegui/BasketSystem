package com.basket.BasketballSystem.temporadas;

import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TemporadaService {

    @Autowired
    TemporadaRepository temporadaRepository;

    public ResponseEntity<String> crearTemporada(Temporada temporada) {

        if(temporada.getNombreTemporada() == null || temporada.getNombreTemporada().isEmpty()) return ResponseEntity.badRequest().body("El nombre de la temporada no puede ser nulo");
        if(temporada.getFechaInicio() == null) return ResponseEntity.badRequest().body("La fecha de inicio de la temporada no puede ser nula");
        if(temporada.getFechaTermino() == null) return ResponseEntity.badRequest().body("La fecha de fin de la temporada no puede ser nula");
        if(temporada.getFechaInicio().isAfter(temporada.getFechaTermino())) return ResponseEntity.badRequest().body("La fecha de inicio no puede ser posterior a la fecha de fin");
        if(temporada.getLiga() == null) return ResponseEntity.badRequest().body("La liga no puede ser nula");
        if(temporada.getLiga().getId() == null) return ResponseEntity.badRequest().body("El id de la liga no puede ser nulo");
        if(temporada.getCantidadEquipos() == null) return ResponseEntity.badRequest().body("La cantidad de equipos no puede ser nula");
        if(temporada.getCantidadEquipos() < 2) return ResponseEntity.badRequest().body("La cantidad de equipos no puede ser menor a 2");

        if(temporada.getCantidadEliminados() == null){
            temporada.setCantidadEliminados(0);
        }
        if(temporada.getEstado() == null){
            temporada.setEstado(Estado.ACTIVA);
        }

        temporadaRepository.save(temporada);
        return ResponseEntity.ok("Temporada creada exitosamente.");

    }


}
