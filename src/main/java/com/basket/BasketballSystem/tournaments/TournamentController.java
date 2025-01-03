package com.basket.BasketballSystem.tournaments;

import com.basket.BasketballSystem.tournaments.DTO.CreateTournamentRequest;
import com.basket.BasketballSystem.tournaments.DTO.TemporadaRequest;
import com.basket.BasketballSystem.tournaments.DTO.TournamentDTO;
import com.basket.BasketballSystem.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
// CHANGE NAME TO TOURNAMENT
@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    TournamentService tournamentService;

    @PostMapping("/createTournament")
    public ResponseEntity<Map<String, Object>> createTournament(@RequestBody CreateTournamentRequest request) {
        Tournament tournament = request.getTournament();
        System.out.println("tournament string -> " + tournament.toString());
        Long userId = request.getUserId();

        try {
            return tournamentService.createTournament(tournament,userId);
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to create tournament", "error", e.getMessage()));
        }

    }

    @PutMapping("/modificarDatosTemporada")
    public ResponseEntity<String> modificarDatosTemporada(@RequestBody Map<String, Object> requestMap) {
        Long temporadaId = Long.parseLong(requestMap.get("temporadaId").toString());
        Estado estado = Estado.valueOf(requestMap.get("estado").toString());

        return tournamentService.modificarDatosTemporada(temporadaId, estado);
    }

    @DeleteMapping("/eliminarArbitro")
    public ResponseEntity<Map<String, Object>> eliminarArbitro(
            @RequestParam("temporadaId") Long temporadaId,
            @RequestParam("arbitroId") String arbitroId) {
        return tournamentService.eliminarArbitroDeTemporada(temporadaId, arbitroId);
    }

//    @PostMapping("/agregarArbitro")
//    public ResponseEntity<Map<String, Object>> agregarArbitro(@RequestBody Map<String, Object> requestMap) {
//        Long temporadaId = Long.parseLong(requestMap.get("temporadaId").toString());
//        String arbitroId = requestMap.get("arbitroId").toString();
//        return TournamentService.agregarArbitro(temporadaId, arbitroId);
//    }

    @GetMapping("/obtenerArbitros")
    public List<Usuario> obtenerArbitros(@RequestParam(name = "idTemporada",required = false) Long temporadaId) {
        return tournamentService.obtenerArbitros(temporadaId);
    }

    @GetMapping("/buscarTemporadasPorNombre")
        public List<Map<String,Object>> buscarTemporadasPorNombre(@RequestParam(name = "nombreTemporada",required = false) String nombreTemporada) {
        return tournamentService.buscarTemporadasPorNombre(nombreTemporada);
    }

    @GetMapping("/obtenerEstadoTemporada")
    public ResponseEntity<Map<String, Object>> obtenerEstadoTemporada(@RequestParam(name = "idTemporada", required = false) Long idTemporada){
        return tournamentService.obtenerEstadoTemporada(idTemporada);
    }

    @PutMapping("/modificarCaracteristicasTemporada")
    public ResponseEntity<Map<String, Object>> modificarCaracteristicasTemporada(@RequestBody TemporadaRequest request) {
        return tournamentService.modificarCaracteristicasTemporada(request);
    }

    @GetMapping("/obtenerCaracteristicasTemporada")
    public ResponseEntity<Map<String, Object>> obtenerCaracteristicasTemporada(@RequestParam(name = "idTemporada", required = false) Long idTemporada){
        return tournamentService.obtenerCaracteristicasTemporada(idTemporada);
    }

    @GetMapping("/getTournament")
    public ResponseEntity<TournamentDTO> getTournamentById(@RequestParam Long tournamentId){
        return tournamentService.getTournamentById(tournamentId);
    }

}
