package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.ligas.LigaRepository;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TemporadaService {

    @Autowired
    TemporadaRepository temporadaRepository;
    @Autowired
    LigaRepository ligaRepository;

    public ResponseEntity<String> crearTemporada(Temporada temporada) {

        if(temporada.getNombreTemporada() == null || temporada.getNombreTemporada().isEmpty()) return ResponseEntity.badRequest().body("El nombre de la temporada no puede ser nulo");
        if(temporada.getFechaInicio() == null) return ResponseEntity.badRequest().body("La fecha de inicio de la temporada no puede ser nula");
        if(temporada.getFechaTermino() == null) return ResponseEntity.badRequest().body("La fecha de fin de la temporada no puede ser nula");
        if(temporada.getFechaInicio().isAfter(temporada.getFechaTermino())) return ResponseEntity.badRequest().body("La fecha de inicio no puede ser posterior a la fecha de fin");
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


    public ResponseEntity<String> asignarLiga(Long temporadaId, Long ligaId) {
        Temporada temporada = temporadaRepository.findById(temporadaId).orElse(null);
        Liga liga = ligaRepository.findById(ligaId).orElse(null);
        if(liga == null) return ResponseEntity.badRequest().body("La liga no existe");
        if(temporada == null) return ResponseEntity.badRequest().body("La temporada no existe");

        temporada.setLiga(liga);
        temporadaRepository.save(temporada);
        return ResponseEntity.ok("Liga asignada exitosamente.");
    }

    public ResponseEntity<String> modificarDatosTemporada(Long temporadaId, Estado estado) {
        Temporada temporada = temporadaRepository.findById(temporadaId).orElse(null);
        if(temporada == null) return ResponseEntity.badRequest().body("La temporada no existe");
        temporada.setEstado(estado);
        temporadaRepository.save(temporada);
        return ResponseEntity.ok("Temporada modificada exitosamente.");
    }





}
