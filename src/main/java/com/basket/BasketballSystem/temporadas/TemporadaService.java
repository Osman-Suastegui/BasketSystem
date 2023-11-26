package com.basket.BasketballSystem.temporadas;

import com.basket.BasketballSystem.exceptions.BadRequestException;
import com.basket.BasketballSystem.ligas.DTO.obtenerLigasDeAdminResponse;
import com.basket.BasketballSystem.ligas.Liga;
import com.basket.BasketballSystem.ligas.LigaRepository;
import com.basket.BasketballSystem.temporadas.DTO.TemporadaRequest;
import com.basket.BasketballSystem.temporadas.DTO.obtenerTemporadasDeLigaResponse;
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

    public ResponseEntity<Map<String, Object>> crearTemporada(Temporada temporada) {
        Map<String, Object> tempNew = new HashMap<>();

        if (temporada.getNombreTemporada() == null || temporada.getNombreTemporada().isEmpty())
            throw new BadRequestException("El nombre de la temporada no puede ser nulo");
        if (temporada.getFechaInicio() == null)
            throw new BadRequestException("La fecha de inicio de la temporada no puede ser nula");
        if (temporada.getFechaTermino() == null)
            throw new BadRequestException("La fecha de termino de la temporada no puede ser nula");
        if (temporada.getFechaInicio().isAfter(temporada.getFechaTermino()))
            throw new BadRequestException("La fecha de inicio no puede ser mayor a la fecha de termino");


        if (temporada.getCantidadPlayoffs() == null) {
            temporada.setCantidadPlayoffs(0);
        }
        if (temporada.getEstado() == null) {
            temporada.setEstado(Estado.INACTIVA);
        }

        temporadaRepository.save(temporada);


        tempNew.put("claveTemporada", temporada.getClaveTemporada().toString());

        tempNew.put("message", "Temporada creada Exitosamente.");
        return ResponseEntity.ok(tempNew);

    }


    public ResponseEntity<Map<String, Object>> asignarLiga(Long temporadaId, Long ligaId) {
        Temporada temporada = temporadaRepository.findById(temporadaId).orElse(null);
        Liga liga = ligaRepository.findById(ligaId).orElse(null);

        if (liga == null) throw new BadRequestException("La liga no existe");
        if (temporada == null) throw new BadRequestException("La temporada no existe");

        temporada.setLiga(liga);
        temporadaRepository.save(temporada);

        Map<String, Object> tempNew = new HashMap<>();
        tempNew.put("message", "Temporada asignada Exitosamente.");

        return ResponseEntity.ok(tempNew);

    }

    public ResponseEntity<String> modificarDatosTemporada(Long temporadaId, Estado estado) {
        Temporada temporada = temporadaRepository.findById(temporadaId).orElse(null);
        if (temporada == null) return ResponseEntity.badRequest().body("La temporada no existe");
        temporada.setEstado(estado);
        temporadaRepository.save(temporada);
        return ResponseEntity.ok("Temporada modificada exitosamente.");
    }


    public ResponseEntity<Map<String, Object>> agregarArbitro(Long temporadaId, String arbitroId) {
        Temporada temporada = temporadaRepository.findById(temporadaId).orElse(null);
        Usuario arbitro = usuarioRepository.findById(arbitroId).orElse(null);
        Map<String, Object> arbitroTemp = new HashMap<>();
        if (temporada == null) throw new BadRequestException("La temporada no existe");
        if (arbitro == null) throw new BadRequestException("El arbitro no existe");

        temporada.getArbitros().add(arbitro);
        temporadaRepository.save(temporada);


        arbitroTemp.put("message", "Arbitro agregado exitosamente.");


        return ResponseEntity.ok(arbitroTemp);

    }

    public List<Usuario> obtenerArbitros(Long temporadaId) {
        Temporada temporada = temporadaRepository.findById(temporadaId).orElse(null);
        if (temporada == null) throw new BadRequestException("La temporada no existe");
        temporada.getArbitros().forEach(arbitro -> arbitro.setPassword(null));
        return temporada.getArbitros();
    }

    public List<Map<String, Object>> buscarTemporadasPorNombre(String nombreTemporada) {

        List<Temporada> temporadas = temporadaRepository.findByNombreTemporadaContaining(nombreTemporada);

        List<Map<String, Object>> temporadasMap = new ArrayList<>();


        for (Temporada temporada : temporadas) {
            Map<String, Object> t = new HashMap<>();

            t.put("claveTemporada", temporada.getClaveTemporada());
            t.put("nombreTemporada", temporada.getNombreTemporada());
            temporadasMap.add(t);
        }


        return temporadasMap;


    }


    public List<obtenerTemporadasDeLigaResponse> obtenerTemporadasDeLiga(Long idLiga) {
        if (idLiga == null) {
            throw new BadRequestException("La liga no puede ser nulo");
        }

        List<Object[]> TemporadasDeLiga = temporadaRepository.findTemporadasForLiga(idLiga);

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
        List<String> arbitros = temporadaRepository.findArbitrosNotInTemporada(temporadaId);
        return arbitros;
    }


    public ResponseEntity<Map<String, Object>> obtenerEstadoTemporada(Long idTemporada) {
        Temporada temporada = temporadaRepository.findById(idTemporada).orElse(null);
        Map<String, Object> arbitroTemp = new HashMap<>();

        arbitroTemp.put("estado", temporada.getEstado().toString());
        return ResponseEntity.ok(arbitroTemp);

    }


    public ResponseEntity<Map<String, Object>> eliminarArbitroDeTemporada(Long temporadaId, String nombreArbitro) {
        temporadaRepository.deleteArbitroFromTemporada(temporadaId, nombreArbitro);
        Map<String, Object> arbitroTemp = new HashMap<>();
        arbitroTemp.put("message", "Arbitro eliminado exitosamente.");
        return ResponseEntity.ok(arbitroTemp);
    }


    public ResponseEntity<Map<String, Object>> modificarCaracteristicasTemporada(TemporadaRequest request) {
        Long idTemporada = request.getClaveTemporada();
        Temporada temporada = temporadaRepository.findById(idTemporada).orElse(null);
        Map<String, Object> tempNew = new HashMap<>();

        if (temporada == null) throw new BadRequestException("La temporada no existe");
        if (request.getCantidadEquipos() == null) throw new BadRequestException("La cantidad de equipos no puede ser nula");
        if (request.getCantidadPlayoffs() == null) throw new BadRequestException("La cantidad de playoffs no puede ser nula");
        if (request.getCantidadEnfrentamientosRegular() == null)
            throw new BadRequestException("La cantidad de enfrentamientos regulares no puede ser nula");

        temporada.setCantidadEquipos(request.getCantidadEquipos());
        temporada.setCantidadPlayoffs(request.getCantidadPlayoffs());
        temporada.setCantidadEnfrentamientosRegular(request.getCantidadEnfrentamientosRegular());
        temporadaRepository.save(temporada);

        tempNew.put("message", "Características de la temporada modificada exitosamente.");
        return ResponseEntity.ok(tempNew);
    }

    public ResponseEntity<Map<String, Object>> obtenerCaracteristicasTemporada(Long idTemporada) {
        Temporada temporada = temporadaRepository.findById(idTemporada).orElse(null);
        Map<String, Object> tempNew = new HashMap<>();

        if (temporada == null) throw new BadRequestException("La temporada no existe");

        tempNew.put("cantidadEquipos", temporada.getCantidadEquipos());
        tempNew.put("cantidadPlayoffs", temporada.getCantidadPlayoffs());
        tempNew.put("cantidadEnfrentamientosRegular", temporada.getCantidadEnfrentamientosRegular());
        return ResponseEntity.ok(tempNew);
    }
}