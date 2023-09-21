package com.basket.BasketballSystem.equipos_temporadas;

import com.basket.BasketballSystem.equipos.EquipoRepository;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EquipoTemporadaService {


    @Autowired
    EquipoTemporadaRepository equipoTemporadaRepository;

    @Autowired
    public EquipoTemporadaService(EquipoTemporadaRepository equipoTemporadaRepository) {
        this.equipoTemporadaRepository = equipoTemporadaRepository;
    }


    public ResponseEntity<String> crearEquipoTemporada(EquipoTemporada equipoTemporada) {

        if(equipoTemporada.getTemporada() == null ){
            return ResponseEntity.badRequest().body("La temporada no puede ser nula o vacia");
        }
        if(equipoTemporada.getEquipo() == null ){
            return ResponseEntity.badRequest().body("El equipo no puede ser nulo o vacio");
        }


        equipoTemporadaRepository.save(equipoTemporada);
        return ResponseEntity.ok("EquipoTemporada creado correctamente");
    }
}
