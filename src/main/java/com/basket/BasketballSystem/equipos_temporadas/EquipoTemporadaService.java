package com.basket.BasketballSystem.equipos_temporadas;

import com.basket.BasketballSystem.equipos.Equipo;
import com.basket.BasketballSystem.equipos.EquipoRepository;
import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EquipoTemporadaService {


    @Autowired
    EquipoTemporadaRepository equipoTemporadaRepository;

    @Autowired
    public EquipoTemporadaService(EquipoTemporadaRepository equipoTemporadaRepository) {
        this.equipoTemporadaRepository = equipoTemporadaRepository;
    }


    public ResponseEntity<Map<String, Object>> crearEquipoTemporada(EquipoTemporada equipoTemporada) {

        if(equipoTemporada.getTemporada() == null ){
            throw new BadRequestException("La temporada no puede ser nula o vacia");
        }
        if(equipoTemporada.getEquipo().getNombre() == null ){
            throw new BadRequestException("El equipo no puede ser nulo o vacio");
        }

        Map<String, Object> equipoTemp = new HashMap<>();
        equipoTemporadaRepository.save(equipoTemporada);
        equipoTemp.put("message", "Arbitro agregado exitosamente.");


        return ResponseEntity.ok(equipoTemp);
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> eliminarEquipoTemporada(EquipoTemporada equipoTemporada) {

        Long claveTemporada = equipoTemporada.getTemporada().getClaveTemporada();
        String nombreEquipo = equipoTemporada.getEquipo().getNombre();


        equipoTemporadaRepository.deleteByTemporadaClaveTemporadaAndEquipoNombre(claveTemporada, nombreEquipo);

            equipoTemporadaRepository.delete(equipoTemporada);
        Map<String, Object> EquipoMap = new HashMap<>();
        EquipoMap.put("message", "Equipo eliminado exitosamente.");
            return ResponseEntity.ok(EquipoMap);
    }



    public ResponseEntity<Map<String, Object>> obtenerEquiposTemporada(Long temporadaId) {

        if (temporadaId == null) {
            throw new BadRequestException("La temporada no existe");
        }

        List<Equipo> equipos = equipoTemporadaRepository.findEquiposByClaveTemporada(temporadaId);



        Map<String, Object> nombreEquipoMap = new HashMap<>();
        for (Equipo equipo : equipos) {
            nombreEquipoMap.put(equipo.getNombre(), equipo.getNombre()); // Utiliza el nombre del equipo como clave
        }

        return ResponseEntity.ok(nombreEquipoMap);
    }



    public List<String> obtenerEquiposNoEnTemporada(Long temporadaId) {


            List<String> equipos = equipoTemporadaRepository.findEquiposNotInTemporada(temporadaId);


            return equipos;
    }
}
