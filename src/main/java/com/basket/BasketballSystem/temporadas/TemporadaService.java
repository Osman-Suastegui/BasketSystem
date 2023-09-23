package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.ligas.LigaRepository;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemporadaService {

    @Autowired
    TemporadaRepository temporadaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    LigaRepository ligaRepository;

    public ResponseEntity<String> crearTemporada(Temporada temporada) {

        if (temporada.getNombreTemporada() == null || temporada.getNombreTemporada().isEmpty())
            return ResponseEntity.badRequest().body("El nombre de la temporada no puede ser nulo");
        if (temporada.getFechaInicio() == null)
            return ResponseEntity.badRequest().body("La fecha de inicio de la temporada no puede ser nula");
        if (temporada.getFechaTermino() == null)
            return ResponseEntity.badRequest().body("La fecha de fin de la temporada no puede ser nula");
        if (temporada.getFechaInicio().isAfter(temporada.getFechaTermino()))
            return ResponseEntity.badRequest().body("La fecha de inicio no puede ser posterior a la fecha de fin");
        if (temporada.getCantidadEquipos() == null)
            return ResponseEntity.badRequest().body("La cantidad de equipos no puede ser nula");
        if (temporada.getCantidadEquipos() < 2)
            return ResponseEntity.badRequest().body("La cantidad de equipos no puede ser menor a 2");

        if (temporada.getCantidadEliminados() == null) {
            temporada.setCantidadEliminados(0);
        }
        if (temporada.getEstado() == null) {
            temporada.setEstado(Estado.ACTIVA);
        }

        temporadaRepository.save(temporada);
        return ResponseEntity.ok("Temporada creada exitosamente.");

    }


    public ResponseEntity<String> asignarLiga(Long temporadaId, Long ligaId) {
        Temporada temporada = temporadaRepository.findById(temporadaId).orElse(null);
        Liga liga = ligaRepository.findById(ligaId).orElse(null);
        if (liga == null) return ResponseEntity.badRequest().body("La liga no existe");
        if (temporada == null) return ResponseEntity.badRequest().body("La temporada no existe");

        temporada.setLiga(liga);
        temporadaRepository.save(temporada);
        return ResponseEntity.ok("Liga asignada exitosamente.");
    }

    public ResponseEntity<String> modificarDatosTemporada(Long temporadaId, Estado estado) {
        Temporada temporada = temporadaRepository.findById(temporadaId).orElse(null);
        if (temporada == null) return ResponseEntity.badRequest().body("La temporada no existe");
        temporada.setEstado(estado);
        temporadaRepository.save(temporada);
        return ResponseEntity.ok("Temporada modificada exitosamente.");
    }


    public ResponseEntity<String> agregarArbitro(Long temporadaId, String arbitroId) {
        Temporada temporada = temporadaRepository.findById(temporadaId).orElse(null);
        Usuario arbitro = usuarioRepository.findById(arbitroId).orElse(null);

        if (temporada == null) return ResponseEntity.badRequest().body("La temporada no existe");
        if (arbitro == null) return ResponseEntity.badRequest().body("El arbitro no existe");

        temporada.getArbitros().add(arbitro);
        temporadaRepository.save(temporada);
        return ResponseEntity.ok("Arbitro agregado exitosamente.");

    }

    public List<Usuario> obtenerArbitros(Long temporadaId) {
        Temporada temporada = temporadaRepository.findById(temporadaId).orElse(null);
        if (temporada == null) throw new BadRequestException("La temporada no existe");
        temporada.getArbitros().forEach(arbitro -> arbitro.setPassword(null));
        return temporada.getArbitros();
    }

    public List<Map<String,Object>> buscarTemporadasPorNombre(String nombreTemporada) {

        List<Temporada> temporadas = temporadaRepository.findByNombreTemporadaContaining(nombreTemporada);

        List<Map<String,Object>> temporadasMap = new ArrayList<>();


        for(Temporada temporada: temporadas){
            Map<String,Object> t = new HashMap<>();

            t.put("claveTemporada",temporada.getClaveTemporada());
            t.put("nombreTemporada",temporada.getNombreTemporada());
            temporadasMap.add(t);
        }


        return temporadasMap;


    }




}