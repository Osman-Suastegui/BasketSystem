package com.basket.BasketballSystem.tournaments;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.ligas.LigaRepository;
import com.basket.BasketballSystem.tournaments.DTO.TemporadaRequest;
import com.basket.BasketballSystem.tournaments.DTO.obtenerTemporadasDeLigaResponse;
import com.basket.BasketballSystem.usuarios.Usuario;
import com.basket.BasketballSystem.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TournamentService {

    @Autowired
    TournamentRepository tournamentRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    LigaRepository ligaRepository;

    public ResponseEntity<Map<String, Object>> crearTemporada(Tournament tournament) {
        Map<String, Object> tempNew = new HashMap<>();

        if (tournament.getNombreTemporada() == null || tournament.getNombreTemporada().isEmpty())
            throw new BadRequestException("El nombre de la temporada no puede ser nulo");
        if (tournament.getStartDate() == null)
            throw new BadRequestException("La fecha de inicio de la temporada no puede ser nula");
        if (tournament.getEndDate() == null)
            throw new BadRequestException("La fecha de termino de la temporada no puede ser nula");
        if (tournament.getStartDate().isAfter(tournament.getEndDate()))
            throw new BadRequestException("La fecha de inicio no puede ser mayor a la fecha de termino");


        if (tournament.getCantidadPlayoffs() == null) {
            tournament.setCantidadPlayoffs(0);
        }
        if (tournament.getEstado() == null) {
            tournament.setEstado(Estado.INACTIVA);
        }

        tournamentRepository.save(tournament);


        tempNew.put("claveTemporada", tournament.getClaveTemporada().toString());

        tempNew.put("message", "Temporada creada Exitosamente.");
        return ResponseEntity.ok(tempNew);

    }


    public ResponseEntity<Map<String, Object>> asignarLiga(Long temporadaId, Long ligaId) {
        Tournament tournament = tournamentRepository.findById(temporadaId).orElse(null);
        Liga liga = ligaRepository.findById(ligaId).orElse(null);

        if (liga == null) throw new BadRequestException("La liga no existe");
        if (tournament == null) throw new BadRequestException("La temporada no existe");

        tournament.setLiga(liga);
        tournamentRepository.save(tournament);

        Map<String, Object> tempNew = new HashMap<>();
        tempNew.put("message", "Temporada asignada Exitosamente.");

        return ResponseEntity.ok(tempNew);

    }

    public ResponseEntity<String> modificarDatosTemporada(Long temporadaId, Estado estado) {
        Tournament tournament = tournamentRepository.findById(temporadaId).orElse(null);
        if (tournament == null) return ResponseEntity.badRequest().body("La temporada no existe");
        tournament.setEstado(estado);
        tournamentRepository.save(tournament);
        return ResponseEntity.ok("Temporada modificada exitosamente.");
    }


    public ResponseEntity<Map<String, Object>> agregarArbitro(Long temporadaId, String arbitroId) {
        Tournament tournament = tournamentRepository.findById(temporadaId).orElse(null);
        Usuario arbitro = usuarioRepository.findById(arbitroId).orElse(null);
        Map<String, Object> arbitroTemp = new HashMap<>();
        if (tournament == null) throw new BadRequestException("La temporada no existe");
        if (arbitro == null) throw new BadRequestException("El arbitro no existe");

        tournament.getArbitros().add(arbitro);
        tournamentRepository.save(tournament);


        arbitroTemp.put("message", "Arbitro agregado exitosamente.");


        return ResponseEntity.ok(arbitroTemp);

    }

    public List<Usuario> obtenerArbitros(Long temporadaId) {
        Tournament tournament = tournamentRepository.findById(temporadaId).orElse(null);
        if (tournament == null) throw new BadRequestException("La temporada no existe");
        tournament.getArbitros().forEach(arbitro -> arbitro.setPassword(null));
        return tournament.getArbitros();
    }

    public List<Map<String, Object>> buscarTemporadasPorNombre(String nombreTemporada) {

        List<Tournament> tournaments = tournamentRepository.findByNameContaining(nombreTemporada);

        List<Map<String, Object>> temporadasMap = new ArrayList<>();


        for (Tournament tournament : tournaments) {
            Map<String, Object> t = new HashMap<>();

            t.put("claveTemporada", tournament.getClaveTemporada());
            t.put("nombreTemporada", tournament.getNombreTemporada());
            temporadasMap.add(t);
        }


        return temporadasMap;


    }


    public List<obtenerTemporadasDeLigaResponse> obtenerTemporadasDeLiga(Long idLiga) {
        if (idLiga == null) {
            throw new BadRequestException("La liga no puede ser nulo");
        }

        List<Object[]> TemporadasDeLiga = tournamentRepository.findTournamentsForLiga(idLiga);

        List<obtenerTemporadasDeLigaResponse> TemporadasLigaRes = new ArrayList<>();

        for (Object[] temp : TemporadasDeLiga) {
            obtenerTemporadasDeLigaResponse temResponse = new obtenerTemporadasDeLigaResponse();

            temResponse.setIdTemporada((Long) temp[0]);
            temResponse.setNombreTemporada((String) temp[1]);
            TemporadasLigaRes.add(temResponse);
        }

        // Llama a la consulta personalizada para obtener las ligas del administrador
        return TemporadasLigaRes;

    }


    public List<String> obtenerArbitrosNoEnTemporada(Long temporadaId) {
        List<String> arbitros = tournamentRepository.findArbitrosNotInTemporada(temporadaId);
        return arbitros;
    }


    public ResponseEntity<Map<String, Object>> obtenerEstadoTemporada(Long idTemporada) {
        Tournament tournament = tournamentRepository.findById(idTemporada).orElse(null);
        Map<String, Object> arbitroTemp = new HashMap<>();

        arbitroTemp.put("estado", tournament.getEstado().toString());
        return ResponseEntity.ok(arbitroTemp);

    }


    public ResponseEntity<Map<String, Object>> eliminarArbitroDeTemporada(Long temporadaId, String nombreArbitro) {
        tournamentRepository.deleteArbitroFromTemporada(temporadaId, nombreArbitro);
        Map<String, Object> arbitroTemp = new HashMap<>();
        arbitroTemp.put("message", "Arbitro eliminado exitosamente.");
        return ResponseEntity.ok(arbitroTemp);
    }


    public ResponseEntity<Map<String, Object>> modificarCaracteristicasTemporada(TemporadaRequest request) {
        Long idTemporada = request.getClaveTemporada();
        Tournament tournament = tournamentRepository.findById(idTemporada).orElse(null);
        Map<String, Object> tempNew = new HashMap<>();

        if (tournament == null) throw new BadRequestException("La temporada no existe");
        if (request.getCantidadEquipos() == null) throw new BadRequestException("La cantidad de equipos no puede ser nula");
        if (request.getCantidadPlayoffs() == null) throw new BadRequestException("La cantidad de playoffs no puede ser nula");
        if (request.getCantidadEnfrentamientosRegular() == null)
            throw new BadRequestException("La cantidad de enfrentamientos regulares no puede ser nula");

        tournament.setCantidadEquipos(request.getCantidadEquipos());
        tournament.setCantidadPlayoffs(request.getCantidadPlayoffs());
        tournament.setCantidadEnfrentamientosRegular(request.getCantidadEnfrentamientosRegular());
        tournamentRepository.save(tournament);

        tempNew.put("message", "Características de la temporada modificada exitosamente.");
        return ResponseEntity.ok(tempNew);
    }

    public ResponseEntity<Map<String, Object>> obtenerCaracteristicasTemporada(Long idTemporada) {
        Tournament tournament = tournamentRepository.findById(idTemporada).orElse(null);
        Map<String, Object> tempNew = new HashMap<>();

        if (tournament == null) throw new BadRequestException("La temporada no existe");

        tempNew.put("cantidadEquipos", tournament.getCantidadEquipos());
        tempNew.put("cantidadPlayoffs", tournament.getCantidadPlayoffs());
        tempNew.put("cantidadEnfrentamientosRegular", tournament.getCantidadEnfrentamientosRegular());
        return ResponseEntity.ok(tempNew);
    }
}