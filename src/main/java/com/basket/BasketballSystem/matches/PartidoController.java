package com.basket.BasketballSystem.matches;

import com.basket.BasketballSystem.matches.DTO.GenerateMatchReq;
import com.basket.BasketballSystem.matches.DTO.GetMatchesRes;
import com.basket.BasketballSystem.matches.DTO.PartidoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Partido")
public class PartidoController {

    @Autowired
    PartidoService partidoService;

    @RequestMapping ("/obtenerPartidosArbitro")
    public List<Map<String,Object>>  obtenerPartidosArbitro(@RequestParam("idArbitro") Long idArbitro, @RequestParam(value = "estatusPartido",required = false) String estatusPartido){
        return partidoService.obtenerPartidosArbitro(idArbitro,estatusPartido);
    }


    @RequestMapping ("/obtenerPartidosEquipo")
    public List<Map<String,Object>>  obtenerPartidosEquipo(@RequestParam("idEquipo") String idEquipo, @RequestParam(value = "estatusPartido",required = false) String estatusPartido ){
        return partidoService.obtenerPartidosEquipo(idEquipo,estatusPartido);
    }


    @RequestMapping ("/obtenerPartidosJugador")
    public List<Map<String,Object>>  obtenerPartidosJugador(@RequestParam("idJugador") Long idJugador){

        return partidoService.obtenerPartidosJugador(idJugador);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @RequestMapping ("/obtenerPartidosTemporada")
    public List<Map<String,Object>>  obtenerPartidosTemporada(@RequestParam("idTemporada") Long idTemporada){

        return partidoService.obtenerPartidosTemporada(idTemporada);
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @PutMapping("/agendar")
    public ResponseEntity<Map<String, Object>> agendarPartido(@RequestBody Map<String, Object> partido){
        Long idPartido = Long.parseLong(partido.get("clavePartido").toString());
        String fecha = partido.get("fechaInicio").toString();
        return partidoService.agendarPartido(idPartido,fecha);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @PostMapping("/generarPartidosTemporada")
    public ResponseEntity<Map<String, Object>> generarPartidosTemporada(@RequestBody Map<String, Object> temporada) {
        Integer idTemporada = (Integer) temporada.get("idTemporada");
        Long idTemporadaLong = idTemporada.longValue();
        return partidoService.generarPartidosTemporada(idTemporadaLong);
    }



    @PreAuthorize("hasRole('ROLE_ADMIN_LIGA')")
    @PutMapping("/asignarArbitro")
        public ResponseEntity<Map<String, Object>> asignarArbitro(@RequestBody Match match){
        Long idPartido = match.getClavePartido();
        Long idArbitro = match.getArbitro().getId();
        return partidoService.asignarArbitro(idPartido,idArbitro);

    }

    @GetMapping("/obtenerPartido")
    public ResponseEntity<PartidoResponse> obtenerPartido(@RequestParam("clavePartido") Long idPartido){
        return partidoService.obtenerPartido(idPartido);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EQUIPO')")
    @GetMapping("/obtenerEquipo1Equipo2")
    public ResponseEntity<Map<String, Object>> obtenerEquipo1Equipo2(@RequestParam("clavePartido") Long idPartido){
        return partidoService.obtenerEquipo1Equipo2(idPartido);
    }

    @PostMapping("/crearPartidosTemporadaRegular")

    public ResponseEntity<Map<String, Object>> generarPartidosTemporadaRegular(@RequestBody Map<String, Object> temporada){
        Integer idTemporada = (Integer) temporada.get("idTemporada");
        int cantidadEnfrentamientosRegular = (Integer) temporada.get("cantidadEnfrentamientosRegular");
        Long idTemporadaLong = idTemporada.longValue();
        return partidoService.crearPartidosTemporadaRegular(idTemporadaLong,cantidadEnfrentamientosRegular);
    }


    @GetMapping("/rankingTemporadaRegular")
    public ResponseEntity<Map<String, Integer>> rankingTemporadaRegular(@RequestParam("idTemporada") Long idTemporada){
        Map<String, Integer> ranking = partidoService.rankingTemporadaRegular(idTemporada);
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }

    @GetMapping("/obtenerPartidosTemporadaRegular")
    public ResponseEntity<Map<String, Map<String, Integer>>> obtenerRankingTemporadaRegular(@RequestParam("idTemporada") Long idTemporada) {
        Map<String, Map<String, Integer>> ranking = partidoService.obtenerRankingTemporadaRegular(idTemporada);
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }

//    solo para tesst
    @PutMapping("/setGanadorAleatorio")
    public ResponseEntity<Map<String, Object>> setGanadorAleatorio(@RequestBody Map<String, Object> partido){
        Long idPartido = Long.parseLong(partido.get("idTemporada").toString());
        partidoService.setGanadorAleatorio(idPartido);
        return ResponseEntity.ok(Map.of("mensaje", "Ganador asignado"));
    }
//
//    @PostMapping("/crearPartidosEliminatorias")
//    public ResponseEntity<Map<String, Object>>  crearPartidosEliminatorias(@RequestBody Map<String, Object> idTemporadaBody){
//        Long idTemporada = Long.parseLong(idTemporadaBody.get("idTemporada").toString());
//        partidoService.crearPartidosEliminatorias(idTemporada);
//        return ResponseEntity.ok(Map.of("mensaje", "Partidos creados"));
//    }

//    fechaInicio
    @GetMapping("/obtenerFechaInicio")
    public ResponseEntity<Map<String, Object>> obtenerFechaInicio(@RequestParam("clavePartido") Long idPartido){
        return partidoService.obtenerFechaInicio(idPartido);
    }

    @GetMapping("/obtenerGanador")
    public ResponseEntity<Map<String, Object>> obtenerGanador(@RequestParam("clavePartido") Long idPartido){
        return partidoService.obtenerGanador(idPartido);
    }

//    @PutMapping("/finalizarPartido")
//    public ResponseEntity<Map<String, Object>> finalizarPartido(@RequestBody Map<String, Object> partido){
//        Long idPartido = Long.parseLong(partido.get("clavePartido").toString());
//        return partidoService.finalizarPartido(idPartido);
//    }
    /*
        Este controlador nos dice el nombre del el arbitro que esta asignado a un partido
    */
    @GetMapping("/obtenerUsuarioArbitroAsignado")
    public ResponseEntity<Map<String, Object>> obtenerNombreUsuarioAsignado(@RequestParam("clavePartido") Long idPartido){
        return partidoService.obtenerUsuarioArbitroAsignado(idPartido);
    }


    @PutMapping("/arbitroIniciaPartidoFecha")
    public ResponseEntity<Map<String, Object>> arbitroIniciaPartidoFecha(@RequestBody Map<String, Object> partido){
        System.out.println(partido);
        Long idPartido = Long.parseLong(partido.get("clavePartido").toString());
        String fecha = partido.get("fechaInicio").toString();
        return partidoService.arbitroIniciaPartidoFecha(idPartido,fecha);
    }

    @PostMapping("/generateMatches")
    public ResponseEntity<Map<String,Object>> generateMatches(@RequestBody GenerateMatchReq req) {
        partidoService.generateMatches(req);

        return ResponseEntity.ok(Map.of("message","success"));
    }


    @GetMapping("/matches")
    public ResponseEntity<List<GetMatchesRes>> generateMatches(@RequestParam Long tournamentId ) {
        List<GetMatchesRes> matches = partidoService.getMatches(tournamentId);
        return ResponseEntity.ok(matches);
    }
}
