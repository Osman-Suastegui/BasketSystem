package com.basket.BasketballSystem.tournaments;

import com.basket.BasketballSystem.tournaments.DTO.TemporadaRequest;
import com.basket.BasketballSystem.tournaments.DTO.obtenerTemporadasDeLigaResponse;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
// CHANGE NAME TO TOURNAMENT
@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    TournamentService TournamentService;

    @PostMapping("/crearTemporada")
    public ResponseEntity<Map<String, Object>> crearTemporada(@RequestBody Tournament tournament) {


        return TournamentService.crearTemporada(tournament);
    }

    @PutMapping("/modificarDatosTemporada")
    public ResponseEntity<String> modificarDatosTemporada(@RequestBody Map<String, Object> requestMap) {
        Long temporadaId = Long.parseLong(requestMap.get("temporadaId").toString());
        Estado estado = Estado.valueOf(requestMap.get("estado").toString());

        return TournamentService.modificarDatosTemporada(temporadaId, estado);
    }

    @DeleteMapping("/eliminarArbitro")
    public ResponseEntity<Map<String, Object>> eliminarArbitro(
            @RequestParam("temporadaId") Long temporadaId,
            @RequestParam("arbitroId") String arbitroId) {
        return TournamentService.eliminarArbitroDeTemporada(temporadaId, arbitroId);
    }

    @PostMapping("/agregarArbitro")
    public ResponseEntity<Map<String, Object>> agregarArbitro(@RequestBody Map<String, Object> requestMap) {
        Long temporadaId = Long.parseLong(requestMap.get("temporadaId").toString());
        String arbitroId = requestMap.get("arbitroId").toString();
        return TournamentService.agregarArbitro(temporadaId, arbitroId);
    }

    @GetMapping("/obtenerArbitros")
    public List<Usuario> obtenerArbitros(@RequestParam(name = "idTemporada",required = false) Long temporadaId) {
        return TournamentService.obtenerArbitros(temporadaId);
    }

    @GetMapping("/buscarTemporadasPorNombre")
        public List<Map<String,Object>> buscarTemporadasPorNombre(@RequestParam(name = "nombreTemporada",required = false) String nombreTemporada) {
        return TournamentService.buscarTemporadasPorNombre(nombreTemporada);
    }

    @GetMapping("/obtenerEstadoTemporada")
    public ResponseEntity<Map<String, Object>> obtenerEstadoTemporada(@RequestParam(name = "idTemporada", required = false) Long idTemporada){
        return TournamentService.obtenerEstadoTemporada(idTemporada);
    }

    @PutMapping("/modificarCaracteristicasTemporada")
    public ResponseEntity<Map<String, Object>> modificarCaracteristicasTemporada(@RequestBody TemporadaRequest request) {
        return TournamentService.modificarCaracteristicasTemporada(request);
    }

    @GetMapping("/obtenerCaracteristicasTemporada")
    public ResponseEntity<Map<String, Object>> obtenerCaracteristicasTemporada(@RequestParam(name = "idTemporada", required = false) Long idTemporada){
        return TournamentService.obtenerCaracteristicasTemporada(idTemporada);
    }

}
